package com.example.githubsearcher.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * Data Transfer Object (DTO) representing the request body
 * for searching GitHub repositories.
 *
 * <p>
 * This DTO is used in the {@code POST /api/github/search} endpoint.
 * It captures user-provided search criteria and applies validation
 * constraints to ensure correct API usage.
 * </p>
 *
 * <p>
 * Validation is handled using Jakarta Bean Validation annotations,
 * and invalid requests will result in a {@code 400 Bad Request}
 * response handled by {@code GlobalExceptionHandler}.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRequestDto {

    /**
     * Search keyword or phrase used to query GitHub repositories.
     *
     * <p>
     * This field is mandatory and must not be blank.
     * Example: "spring boot"
     * </p>
     */
    @NotBlank(message = "Query must not be empty")
    private String query;

    /**
     * Optional programming language filter.
     *
     * <p>
     * If provided, the GitHub API query will include
     * a language filter (e.g., language:Java).
     * </p>
     */
    private String language;

    /**
     * Optional sorting criteria for search results.
     *
     * <p>
     * Allowed values:
     * <ul>
     *     <li>{@code stars}</li>
     *     <li>{@code forks}</li>
     *     <li>{@code updated}</li>
     * </ul>
     * </p>
     *
     * <p>
     * If an invalid value is provided, validation will fail
     * and return a {@code 400 Bad Request}.
     * </p>
     */
    @Pattern(
            regexp = "stars|forks|updated",
            message = "Sort must be one of: stars, forks, updated"
    )
    private String sort;
}
