package com.example.githubsearcher.service;

import com.example.githubsearcher.dto.RepositoryResponseDto;
import com.example.githubsearcher.dto.SearchRequestDto;
import com.example.githubsearcher.dto.SearchResponseDto;

import java.util.List;

/**
 * Service interface defining business operations related to
 * GitHub repository search and retrieval.
 *
 * <p>
 * This interface abstracts the business logic layer and ensures
 * loose coupling between the controller layer and the implementation.
 * </p>
 *
 * <p>
 * Implemented by {@code GitHubServiceImpl}.
 * </p>
 */
public interface GitHubService {

    /**
     * Searches repositories from the GitHub API based on
     * the provided request criteria and stores them in the database.
     *
     * <p>
     * Responsibilities include:
     * <ul>
     *     <li>Calling the GitHub API via client layer</li>
     *     <li>Mapping API DTOs to entities</li>
     *     <li>Persisting repositories in the database</li>
     *     <li>Returning response DTOs</li>
     * </ul>
     * </p>
     *
     * @param request SearchRequestDto containing query, language, and sort criteria
     * @return SearchResponseDto containing operation message and stored repositories
     */
    SearchResponseDto searchAndSaveRepositories(SearchRequestDto request);

    /**
     * Retrieves repositories stored in the database with optional
     * filtering and sorting.
     *
     * <p>
     * Filtering:
     * <ul>
     *     <li>By programming language</li>
     *     <li>By minimum star count</li>
     * </ul>
     * </p>
     *
     * <p>
     * Sorting options:
     * <ul>
     *     <li>stars</li>
     *     <li>forks</li>
     *     <li>updated</li>
     * </ul>
     * </p>
     *
     * @param language Optional language filter
     * @param minStars Optional minimum star filter
     * @param sort     Sorting criteria
     * @return List of RepositoryResponseDto
     */
    List<RepositoryResponseDto> getStoredRepositories(
            String language,
            Integer minStars,
            String sort
    );
}
