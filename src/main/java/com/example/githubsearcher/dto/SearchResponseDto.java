package com.example.githubsearcher.dto;

import lombok.*;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing the response
 * of the GitHub repository search operation.
 *
 * <p>
 * This DTO is returned by the {@code POST /api/github/search} endpoint
 * after fetching repositories from the GitHub API and persisting them
 * into the database.
 * </p>
 *
 * <p>
 * It contains:
 * <ul>
 *     <li>A status/message describing the outcome of the operation</li>
 *     <li>A list of repository details returned to the client</li>
 * </ul>
 * </p>
 *
 * <p>
 * The repository details are represented using {@link RepositoryResponseDto},
 * ensuring separation between persistence models and API response models.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponseDto {

    /**
     * Message indicating the result of the search operation.
     *
     * <p>
     * Examples:
     * <ul>
     *     <li>"Repositories fetched and saved successfully"</li>
     *     <li>"No repositories found"</li>
     * </ul>
     * </p>
     */
    private String message;

    /**
     * List of repositories returned from the search operation.
     *
     * <p>
     * This list may be empty if no repositories match the search criteria.
     * </p>
     */
    private List<RepositoryResponseDto> repositories;
}
