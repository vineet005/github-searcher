package com.example.githubsearcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the GitHub Repository Searcher application.
 *
 * <p>
 * The {@link SpringBootApplication} annotation enables:
 * <ul>
 *     <li>Auto-configuration</li>
 *     <li>Component scanning</li>
 *     <li>Configuration properties support</li>
 * </ul>
 * </p>
 *
 * <p>
 * When the application starts:
 * <ul>
 *     <li>Spring Boot initializes the application context</li>
 *     <li>All @Component, @Service, @Repository, and @Configuration classes are registered</li>
 *     <li>Embedded Tomcat server is started</li>
 * </ul>
 * </p>
 *
 * <p>
 * Default server port: 8080 (unless overridden in application.properties)
 * </p>
 */
@SpringBootApplication
public class GithubsearcherApplication {

    /**
     * Application bootstrap method.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GithubsearcherApplication.class, args);
    }

}
