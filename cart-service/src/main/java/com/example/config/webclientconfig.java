package com.example.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class webclientconfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                 .baseUrl("http://localhost:8081")// Set the base URL for the Product Service
                 .build();
    }
}
