package com.acmeinsurance.architecture;

import org.junit.jupiter.api.Test;
import org.springdoc.core.models.GroupedOpenApi;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiTest {

    @Test
    void shouldCreateOpenApiGroupForControllers() {
        GroupedOpenApi openApi = GroupedOpenApi.builder().group("acme-insurance")
                .packagesToScan("com.acmeinsurance.infrastructure.web.controller").build();

        assertThat(openApi).isNotNull();
        assertThat(openApi.getGroup()).isEqualTo("acme-insurance");
    }
}
