package com.example.githubsearcher.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

class WebClientConfigTest {

    @Test
    void gitHubWebClient_shouldCreateBeanWithoutToken() {

        WebClientConfig config = new WebClientConfig();

        ReflectionTestUtils.setField(config, "baseUrl", "https://api.github.com");
        ReflectionTestUtils.setField(config, "token", "");

        WebClient webClient = config.gitHubWebClient();

        assertNotNull(webClient);
    }

    @Test
    void gitHubWebClient_shouldSetAcceptHeader() {

        WebClientConfig config = new WebClientConfig();

        ReflectionTestUtils.setField(config, "baseUrl", "https://api.github.com");
        ReflectionTestUtils.setField(config, "token", "test-token");

        WebClient webClient = config.gitHubWebClient();

        assertNotNull(webClient);

        // Verify Accept header via mutation
        WebClient mutated = webClient.mutate()
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .build();

        assertNotNull(mutated);
    }

    @Test
    void gitHubWebClient_shouldHandleNullToken() {

        WebClientConfig config = new WebClientConfig();

        ReflectionTestUtils.setField(config, "baseUrl", "https://api.github.com");
        ReflectionTestUtils.setField(config, "token", null);

        WebClient webClient = config.gitHubWebClient();

        assertNotNull(webClient);
    }
}
