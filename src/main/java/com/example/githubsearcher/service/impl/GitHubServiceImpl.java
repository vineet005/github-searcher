package com.example.githubsearcher.service.impl;

import com.example.githubsearcher.client.GitHubApiClient;
import com.example.githubsearcher.dto.RepositoryResponseDto;
import com.example.githubsearcher.dto.SearchRequestDto;
import com.example.githubsearcher.dto.SearchResponseDto;
import com.example.githubsearcher.dto.github.GitHubSearchResponseDto;
import com.example.githubsearcher.entity.RepositoryEntity;
import com.example.githubsearcher.mapper.RepositoryMapper;
import com.example.githubsearcher.repository.RepositoryEntityRepository;
import com.example.githubsearcher.repository.specification.RepositorySpecification;
import com.example.githubsearcher.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link GitHubService}.
 *
 * <p>
 * This class contains the core business logic of the application.
 * Responsibilities include:
 * <ul>
 *     <li>Calling GitHub API via {@link GitHubApiClient}</li>
 *     <li>Mapping external DTOs to internal entities</li>
 *     <li>Persisting repositories in the database</li>
 *     <li>Retrieving repositories with dynamic filtering and sorting</li>
 * </ul>
 * </p>
 *
 * <p>
 * Annotated with {@link Service} to indicate business layer component.
 * Uses constructor injection via {@link RequiredArgsConstructor}.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class GitHubServiceImpl implements GitHubService {

    /**
     * Client used to communicate with GitHub API.
     */
    private final GitHubApiClient gitHubApiClient;

    /**
     * Repository used for database operations.
     */
    private final RepositoryEntityRepository repository;

    /**
     * Searches repositories using the GitHub API and stores them in the database.
     *
     * <p>
     * Flow:
     * <ol>
     *     <li>Call GitHub API</li>
     *     <li>Convert API DTOs to Entities</li>
     *     <li>Bulk save (UPSERT behavior via saveAll)</li>
     *     <li>Return mapped response DTOs</li>
     * </ol>
     * </p>
     *
     * <p>
     * Annotated with {@link Transactional} to ensure atomic database operation.
     * </p>
     *
     * @param request Search criteria (query, language, sort)
     * @return SearchResponseDto containing saved repositories
     */
    @Override
    @Transactional
    public SearchResponseDto searchAndSaveRepositories(SearchRequestDto request) {

        GitHubSearchResponseDto response =
                gitHubApiClient.searchRepositories(
                        request.getQuery(),
                        request.getLanguage(),
                        request.getSort()
                );

        if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
            return SearchResponseDto.builder()
                    .message("No repositories found")
                    .repositories(List.of())
                    .build();
        }

        // Convert GitHub DTO → Entity
        List<RepositoryEntity> entities =
                RepositoryMapper.toEntityList(response.getItems());

        // Bulk UPSERT (insert or update existing by primary key)
        repository.saveAll(entities);

        // Convert to Response DTO
        List<RepositoryResponseDto> result =
                RepositoryMapper.toResponseDtoList(entities);

        return SearchResponseDto.builder()
                .message("Repositories fetched and saved successfully")
                .repositories(result)
                .build();
    }

    /**
     * Retrieves stored repositories from the database
     * with optional filtering and sorting.
     *
     * <p>
     * Filtering is implemented using JPA Specifications.
     * Sorting is built dynamically based on input parameter.
     * </p>
     *
     * @param language Optional programming language filter
     * @param minStars Optional minimum star filter
     * @param sort     Sorting field (stars, forks, updated)
     * @return List of RepositoryResponseDto
     */
    @Override
    public List<RepositoryResponseDto> getStoredRepositories(
            String language,
            Integer minStars,
            String sort
    ) {

        Specification<RepositoryEntity> spec = Specification
                .where(RepositorySpecification.hasLanguage(language))
                .and(RepositorySpecification.hasMinStars(minStars));

        Sort sortOrder = buildSort(sort);

        List<RepositoryEntity> entities =
                repository.findAll(spec, sortOrder);

        return RepositoryMapper.toResponseDtoList(entities);
    }

    /**
     * Builds dynamic sorting configuration.
     *
     * <p>
     * Supported sort options:
     * <ul>
     *     <li>stars (default)</li>
     *     <li>forks</li>
     *     <li>updated</li>
     * </ul>
     * </p>
     *
     * @param sort Sorting parameter
     * @return Sort configuration
     */
    private Sort buildSort(String sort) {

        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "stars");
        }

        return switch (sort) {
            case "stars" -> Sort.by(Sort.Direction.DESC, "stars");
            case "forks" -> Sort.by(Sort.Direction.DESC, "forks");
            case "updated" -> Sort.by(Sort.Direction.DESC, "lastUpdated");
            default -> Sort.by(Sort.Direction.DESC, "stars");
        };
    }
}
