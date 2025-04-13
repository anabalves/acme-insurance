package com.acmeinsurance;

import com.acmeinsurance.config.CatalogCacheProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CatalogCacheProperties.class)
public class AcmeInsuranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcmeInsuranceApplication.class, args);
    }

}
