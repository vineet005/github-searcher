package com.example.githubsearcher.dto.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

/**
 * DTO representing a single repository item returned
 * by the GitHub Search Repositories API.
 *
 * <p>
 * This class maps the JSON structure received from:
 * {@code https://api.github.com/search/repositories}
 * </p>
 *
 * <p>
 * It is used internally to deserialize GitHub API responses
 * before converting them into {@code RepositoryEntity} using
 * {@code RepositoryMapper}.
 * </p>
 *
 * <p>
 * Any additional JSON properties returned by GitHub that are not
 * explicitly mapped here are ignored using {@link JsonIgnoreProperties}.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepositoryDto {

    /**
     * Unique identifier of the repository assigned by GitHub.
     */
    private Long id;

    /**
     * Name of the repository.
     */
    private String name;

    /**
     * Description of the repository.
     * May be null if not provided by the repository owner.
     */
    private String description;

    /**
     * Owner details of the repository.
     *
     * <p>
     * This is a nested object in the GitHub API response.
     * Only the {@code login} field is mapped via {@link GitHubOwnerDto}.
     * </p>
     */
    private GitHubOwnerDto owner;

    /**
     * Primary programming language used in the repository.
     * May be null if not specified.
     */
    private String language;

    /**
     * Total number of stars (stargazers).
     *
     * <p>
     * Mapped from JSON property {@code stargazers_count}.
     * </p>
     */
    @JsonProperty("stargazers_count")
    private Integer stars;

    /**
     * Total number of forks.
     *
     * <p>
     * Mapped from JSON property {@code forks_count}.
     * </p>
     */
    @JsonProperty("forks_count")
    private Integer forks;

    /**
     * Timestamp indicating when the repository was last updated.
     *
     * <p>
     * Mapped from JSON property {@code updated_at}.
     * Stored as {@link Instant} for precise, timezone-safe handling.
     * </p>
     */
    @JsonProperty("updated_at")
    private Instant updatedAt;
}
