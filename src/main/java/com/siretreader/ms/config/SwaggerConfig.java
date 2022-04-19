package com.siretreader.ms.config;

import java.util.TreeMap;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /**
     * Sort the schemata alphabetically.
     */
    @Bean
    public OpenApiCustomiser sortSchemataAlphabetically() {
        return openApi -> {
            final var schemata = openApi.getComponents().getSchemas();
            openApi.getComponents().setSchemas(new TreeMap<>(schemata));
        };
    }
}
