package com.example.githubsearcher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class responsible for creating and configuring
 * the {@link WebClient} bean used to communicate with the GitHub API.
 *
 * <p>
 * This configuration centralizes:
 * <ul>
 *     <li>GitHub base URL</li>
 *     <li>Default HTTP headers</li>
 *     <li>Optional authentication token support</li>
 * </ul>
 * </p>
 *
 * <p>
 * The base URL and token are injected from {@code application.properties}.
 * </p>
 */
@Configuration
public class WebClientConfig {

    /**
     * Base URL for the GitHub API.
     *
     * <p>
     * Example: https://api.github.com
     * </p>
     */
    @Value("${github.api.base-url}")
    private String baseUrl;

    /**
     * Optional GitHub personal access token.
     *
     * <p>
     * If provided, it increases the API rate limit
     * from 60 requests/hour to 5000 requests/hour.
     * </p>
     */
    @Value("${github.api.token:}")
    private String token;

    /**
     * Creates a configured {@link WebClient} bean for GitHub API calls.
     *
     * <p>
     * Configuration includes:
     * <ul>
     *     <li>Base URL</li>
     *     <li>Accept header (application/vnd.github+json)</li>
     *     <li>Optional Authorization header if token is present</li>
     * </ul>
     * </p>
     *
     * @return configured WebClient instance
     */
    @Bean
    public WebClient gitHubWebClient() {

        WebClient.Builder builder = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json");

        // If token is present, attach Authorization header
        if (token != null && !token.isBlank()) {
            builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }

        return builder.build();
    }
}
