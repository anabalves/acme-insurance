package com.acmeinsurance;

import com.acmeinsurance.config.CatalogCacheProperties;
import com.acmeinsurance.config.KafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({CatalogCacheProperties.class, KafkaProperties.class})
public class AcmeInsuranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcmeInsuranceApplication.class, args);
    }

}
