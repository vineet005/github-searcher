package com.example.githubsearcher.controller;

import com.example.githubsearcher.dto.RepositoryResponseDto;
import com.example.githubsearcher.dto.SearchRequestDto;
import com.example.githubsearcher.dto.SearchResponseDto;
import com.example.githubsearcher.service.GitHubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing endpoints for interacting with
 * GitHub repository search functionality.
 *
 * <p>
 * Base URL: {@code /api/github}
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 *     <li>Accept search requests</li>
 *     <li>Trigger GitHub API calls via service layer</li>
 *     <li>Persist results into the database</li>
 *     <li>Retrieve stored repositories with filtering and sorting</li>
 * </ul>
 * </p>
 *
 * <p>
 * This controller delegates business logic to {@link GitHubService}
 * and does not contain any business processing logic itself,
 * ensuring proper separation of concerns.
 * </p>
 */
@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
public class GitHubController {

    /**
     * Service layer dependency for handling business logic.
     */
    private final GitHubService gitHubService;

    /**
     * Searches GitHub repositories based on the provided criteria
     * and stores the results in the database.
     *
     * <p>
     * Endpoint: {@code POST /api/github/search}
     * </p>
     *
     * <p>
     * The request body is validated using Jakarta Bean Validation.
     * Invalid inputs result in a {@code 400 Bad Request}.
     * </p>
     *
     * @param request Search criteria including query, language, and sort option
     * @return SearchResponseDto containing operation message and repository list
     */
    @PostMapping("/search")
    public SearchResponseDto searchRepositories(
            @Valid @RequestBody SearchRequestDto request
    ) {
        return gitHubService.searchAndSaveRepositories(request);
    }

    /**
     * Retrieves stored repositories from the database with optional
     * filtering and sorting.
     *
     * <p>
     * Endpoint: {@code GET /api/github/repositories}
     * </p>
     *
     * <p>
     * Query Parameters:
     * <ul>
     *     <li>{@code language} (optional) - Filter by programming language</li>
     *     <li>{@code minStars} (optional) - Minimum star count filter</li>
     *     <li>{@code sort} (optional) - Sorting field (default: stars)</li>
     * </ul>
     * </p>
     *
     * @param language Optional programming language filter
     * @param minStars Optional minimum star count filter
     * @param sort     Optional sorting field (stars, forks, updated)
     * @return List of RepositoryResponseDto matching the filter criteria
     */
    @GetMapping("/repositories")
    public List<RepositoryResponseDto> getRepositories(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Integer minStars,
            @RequestParam(required = false, defaultValue = "stars") String sort
    ) {
        return gitHubService.getStoredRepositories(language, minStars, sort);
    }
}
