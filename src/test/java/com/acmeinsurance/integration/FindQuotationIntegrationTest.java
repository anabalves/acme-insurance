package com.acmeinsurance.integration;

import com.acmeinsurance.AcmeInsuranceApplication;
import com.acmeinsurance.infrastructure.integration.dto.response.OfferCatalogResponse;
import com.acmeinsurance.infrastructure.integration.dto.response.ProductCatalogResponse;
import com.acmeinsurance.util.JsonUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AcmeInsuranceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class FindQuotationIntegrationTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    @Qualifier("productCatalogRedisTemplate")
    protected RedisTemplate<String, ProductCatalogResponse> productRedisTemplate;

    @Autowired
    @Qualifier("offerCatalogRedisTemplate")
    protected RedisTemplate<String, OfferCatalogResponse> offerRedisTemplate;

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
            .withDatabaseName("db_test").withUsername("user").withPassword("password")
            .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));

    @Container
    static final GenericContainer<?> REDIS = new GenericContainer<>(DockerImageName.parse("redis:7"))
            .withExposedPorts(6379).waitingFor(Wait.forListeningPort());

    @Container
    static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.2.1"))
            .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "false").withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", "1")
            .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(60)));

    @Container
    static final GenericContainer<?> WIREMOCK = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:3.4.2"))
            .withExposedPorts(8080)
            .withCopyFileToContainer(MountableFile.forClasspathResource("wiremock"), "/home/wiremock")
            .withCommand("--root-dir=/home/wiremock", "--global-response-templating", "--enable-stub-cors", "--verbose")
            .waitingFor(Wait.forHttp("/__admin/mappings").forStatusCode(200));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        POSTGRES.start();
        REDIS.start();
        KAFKA.start();
        WIREMOCK.start();

        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.data.redis.host", REDIS::getHost);
        registry.add("spring.data.redis.port", () -> REDIS.getMappedPort(6379));
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
        registry.add("KAFKA_BOOTSTRAP_SERVERS", KAFKA::getBootstrapServers);

        String wm = "http://" + WIREMOCK.getHost() + ":" + WIREMOCK.getMappedPort(8080);
        registry.add("feign.catalog.product.api.baseurl", () -> wm + "/products");
        registry.add("feign.catalog.offer.api.baseurl", () -> wm + "/offers");

        registry.add("acme.kafka.topics.quotation-created", () -> "quotation.created");
        registry.add("acme.kafka.topics.policy-issued", () -> "policy.issued");
        registry.add("acme.kafka.consumer.group-id", () -> "test-group-" + System.currentTimeMillis());
    }

    @BeforeAll
    static void createKafkaTopics() {
        await().atMost(20, TimeUnit.SECONDS).until(() -> {
            try (AdminClient client = AdminClient.create(Map.of("bootstrap.servers", KAFKA.getBootstrapServers()))) {
                return !client.describeCluster().nodes().get().isEmpty();
            } catch (Exception e) {
                return false;
            }
        });

        try (AdminClient admin = AdminClient.create(Map.of("bootstrap.servers", KAFKA.getBootstrapServers()))) {
            admin.createTopics(List.of(new NewTopic("quotation.created", 1, (short) 1),
                    new NewTopic("policy.issued", 1, (short) 1))).all().get();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException("Kafka topic setup failed", e);
        }
    }

    @Test
    @DisplayName("Should successfully return a quotation by ID")
    void shouldReturnQuotationSuccessfullyById() throws Exception {
        // given
        String requestJson = JsonUtils.getJsonStringFromFile("quotation/request", "create-success");

        MvcResult createResult = mockMvc
                .perform(post("/quotations").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isCreated()).andReturn();

        Map<String, Object> createdResponse = JsonUtils.asMap(createResult.getResponse().getContentAsString());
        Integer quotationId = (Integer) createdResponse.get("id");

        String expectedResponse = JsonUtils.getJsonStringFromFile("quotation/response", "find-by-id-success");

        // when
        MvcResult result = mockMvc.perform(get("/quotations/{id}", quotationId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        // then
        String actual = result.getResponse().getContentAsString();
        assertThat(JsonUtils.compareJsonIgnoringFields(actual, expectedResponse, "createdAt", "updatedAt", "id",
                "insurancePolicyId")).isTrue();
    }

    @Test
    @DisplayName("Should return 404 when quotation does not exist")
    void shouldReturnNotFoundWhenQuotationDoesNotExist() throws Exception {
        // given
        String expectedResponse = JsonUtils.getJsonStringFromFile("quotation/response", "find-by-id-not-found");

        // when
        MvcResult result = mockMvc.perform(get("/quotations/1001").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        // then
        String actual = result.getResponse().getContentAsString();
        assertThat(JsonUtils.compareJsonIgnoringFields(actual, expectedResponse)).isTrue();
    }
}
