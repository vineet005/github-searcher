package com.example.githubsearcher.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Entity class representing a GitHub repository stored in the system.
 *
 * <p>
 * This entity maps to the {@code repositories} table in the PostgreSQL database.
 * It stores repository details fetched from the GitHub Search API.
 * </p>
 *
 * <p>
 * The GitHub repository ID is used as the primary key to ensure:
 * <ul>
 *     <li>Uniqueness of repositories</li>
 *     <li>Efficient upsert operations (insert if new, update if existing)</li>
 * </ul>
 * </p>
 *
 * <p>
 * This entity is populated using data received from the GitHub REST API
 * and persisted using Spring Data JPA.
 * </p>
 *
 * @author
 */
@Entity
@Table(name = "repositories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositoryEntity {

    /**
     * Unique identifier of the repository provided by GitHub.
     *
     * <p>
     * This is used as the primary key to prevent duplication and
     * allow automatic update (upsert) behavior.
     * </p>
     */
    @Id
    @Column(name = "repo_id")
    private Long id;

    /**
     * Name of the GitHub repository.
     *
     * <p>
     * Cannot be null.
     * </p>
     */
    @Column(nullable = false)
    private String name;

    /**
     * Description of the repository.
     *
     * <p>
     * Maximum length is 2000 characters to accommodate longer descriptions.
     * This field is optional in GitHub API but stored if present.
     * </p>
     */
    @Column(length = 2000)
    private String description;

    /**
     * Username of the repository owner.
     *
     * <p>
     * Extracted from GitHub API response (owner.login).
     * Cannot be null.
     * </p>
     */
    @Column(nullable = false)
    private String owner;

    /**
     * Primary programming language used in the repository.
     *
     * <p>
     * This field is optional as some repositories may not specify a language.
     * </p>
     */
    private String language;

    /**
     * Total number of stars (stargazers) the repository has received.
     *
     * <p>
     * Cannot be null. Used for filtering and sorting operations.
     * </p>
     */
    @Column(nullable = false)
    private Integer stars;

    /**
     * Total number of forks of the repository.
     *
     * <p>
     * Cannot be null. Used for filtering and sorting operations.
     * </p>
     */
    @Column(nullable = false)
    private Integer forks;

    /**
     * Timestamp indicating when the repository was last updated on GitHub.
     *
     * <p>
     * Stored as {@link Instant} to ensure timezone-safe and precise date-time handling.
     * Cannot be null.
     * </p>
     */
    @Column(nullable = false)
    private Instant lastUpdated;
}
