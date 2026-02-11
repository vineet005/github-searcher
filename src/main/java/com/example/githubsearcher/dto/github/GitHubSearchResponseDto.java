package com.example.githubsearcher.dto.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * DTO representing the top-level response from the
 * GitHub Search Repositories API.
 *
 * <p>
 * This class maps the JSON response returned by:
 * {@code https://api.github.com/search/repositories}
 * </p>
 *
 * <p>
 * Example response structure:
 * <pre>
 * {
 *   "total_count": 1234,
 *   "incomplete_results": false,
 *   "items": [
 *     {
 *       "id": 123,
 *       "name": "example-repo",
 *       ...
 *     }
 *   ]
 * }
 * </pre>
 * </p>
 *
 * <p>
 * Only the {@code items} field is mapped because it contains
 * the list of repositories relevant to this application.
 * Other fields (e.g., total_count, incomplete_results)
 * are ignored using {@link JsonIgnoreProperties}.
 * </p>
 *
 * <p>
 * This DTO is used internally by {@code GitHubApiClient}
 * and later converted into {@code RepositoryEntity}
 * using {@code RepositoryMapper}.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubSearchResponseDto {

    /**
     * List of repositories returned by the GitHub search query.
     *
     * <p>
     * Each item represents a single repository and is mapped
     * to {@link GitHubRepositoryDto}.
     * </p>
     */
    private List<GitHubRepositoryDto> items;
}
