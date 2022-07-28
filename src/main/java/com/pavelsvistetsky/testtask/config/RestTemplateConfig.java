package com.pavelsvistetsky.testtask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate cameraRestTemplate(@Value("${com.pavelsvistetsky.camera-server.base-url}") String baseUrl) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
        return restTemplate;
    }

    /**
     * If there is another external API for camera source/token data
     */
    @Bean
    public RestTemplate externalDataRestTemplate(@Value("${com.pavelsvistetsky.camera-server.external-data-base-url}") String baseUrl) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
        return restTemplate;
    }
}
