package com.example.githubsearcher.dto;

import lombok.*;

import java.time.Instant;

/**
 * Data Transfer Object (DTO) representing repository details
 * returned to the client via REST APIs.
 *
 * <p>
 * This DTO is used in:
 * <ul>
 *     <li>Search API response after fetching from GitHub</li>
 *     <li>Retrieve API response when fetching from database</li>
 * </ul>
 * </p>
 *
 * <p>
 * It is mapped from {@code RepositoryEntity} using {@code RepositoryMapper}
 * to ensure separation between persistence layer and API layer.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositoryResponseDto {

    /**
     * Unique GitHub repository identifier.
     */
    private Long id;

    /**
     * Name of the repository.
     */
    private String name;

    /**
     * Description of the repository.
     */
    private String description;

    /**
     * Username of the repository owner.
     */
    private String owner;

    /**
     * Primary programming language used in the repository.
     */
    private String language;

    /**
     * Total number of stars (stargazers) of the repository.
     *
     * <p>
     * Used for sorting and filtering operations.
     * </p>
     */
    private Integer stars;

    /**
     * Total number of forks of the repository.
     *
     * <p>
     * Used for sorting operations.
     * </p>
     */
    private Integer forks;

    /**
     * Timestamp indicating when the repository was last updated on GitHub.
     *
     * <p>
     * Represented as {@link Instant} for accurate and timezone-safe handling.
     * </p>
     */
    private Instant lastUpdated;
}
