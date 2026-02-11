package com.example.githubsearcher.dto.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * DTO representing the owner object returned by the GitHub Search API.
 *
 * <p>
 * This class is used internally to map the nested {@code owner}
 * JSON object from the GitHub repository response.
 * </p>
 *
 * <p>
 * Example GitHub API response snippet:
 * <pre>
 * {
 *   "owner": {
 *     "login": "octocat"
 *   }
 * }
 * </pre>
 * </p>
 *
 * <p>
 * Only the required fields are mapped. Any additional properties
 * returned by GitHub are ignored using {@link JsonIgnoreProperties}.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubOwnerDto {

    /**
     * Username (login) of the repository owner on GitHub.
     *
     * <p>
     * This value is later mapped to the {@code owner} field
     * in {@code RepositoryEntity}.
     * </p>
     */
    private String login;
}
