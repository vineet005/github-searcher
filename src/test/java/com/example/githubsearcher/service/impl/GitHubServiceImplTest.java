package com.example.githubsearcher.service.impl;

import com.example.githubsearcher.client.GitHubApiClient;
import com.example.githubsearcher.dto.RepositoryResponseDto;
import com.example.githubsearcher.dto.SearchRequestDto;
import com.example.githubsearcher.dto.SearchResponseDto;
import com.example.githubsearcher.dto.github.GitHubOwnerDto;
import com.example.githubsearcher.dto.github.GitHubRepositoryDto;
import com.example.githubsearcher.dto.github.GitHubSearchResponseDto;
import com.example.githubsearcher.entity.RepositoryEntity;
import com.example.githubsearcher.mapper.RepositoryMapper;
import com.example.githubsearcher.repository.RepositoryEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GitHubServiceImplTest {

    private GitHubServiceImpl gitHubService;

    private GitHubApiClient gitHubApiClient;
    private RepositoryEntityRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gitHubApiClient = mock(GitHubApiClient.class);
        repository = mock(RepositoryEntityRepository.class);
        gitHubService = new GitHubServiceImpl(gitHubApiClient, repository);
    }

    @Test
    void testSearchAndSaveRepositories_Success() {
        // Arrange
        SearchRequestDto request = new SearchRequestDto();
        request.setQuery("springboot");
        request.setLanguage("java");
        request.setSort("stars");

        GitHubRepositoryDto gitHubRepo = new GitHubRepositoryDto();
        gitHubRepo.setId(1L);
        gitHubRepo.setName("repo1");
        gitHubRepo.setLanguage("java");
        gitHubRepo.setStars(100);
        gitHubRepo.setForks(10);
        gitHubRepo.setOwner(new GitHubOwnerDto("owner1"));
        gitHubRepo.setUpdatedAt(Instant.now());

        GitHubSearchResponseDto apiResponse = new GitHubSearchResponseDto();
        apiResponse.setItems(List.of(gitHubRepo));

        when(gitHubApiClient.searchRepositories(anyString(), anyString(), anyString()))
                .thenReturn(apiResponse);

        try (MockedStatic<RepositoryMapper> mapper = mockStatic(RepositoryMapper.class)) {
            RepositoryEntity entity = RepositoryEntity.builder()
                    .id(1L).name("repo1").language("java")
                    .stars(100).forks(10).owner("owner1").lastUpdated(Instant.now())
                    .build();
            mapper.when(() -> RepositoryMapper.toEntityList(apiResponse.getItems()))
                    .thenReturn(List.of(entity));

            RepositoryResponseDto responseDto = RepositoryResponseDto.builder()
                    .id(1L).name("repo1").language("java")
                    .stars(100).forks(10).owner("owner1").lastUpdated(Instant.now())
                    .build();
            mapper.when(() -> RepositoryMapper.toResponseDtoList(List.of(entity)))
                    .thenReturn(List.of(responseDto));

            // Act
            SearchResponseDto result = gitHubService.searchAndSaveRepositories(request);

            // Assert
            assertNotNull(result);
            assertEquals("Repositories fetched and saved successfully", result.getMessage());
            assertEquals(1, result.getRepositories().size());
            assertEquals("repo1", result.getRepositories().get(0).getName());

            verify(gitHubApiClient, times(1)).searchRepositories("springboot", "java", "stars");
            verify(repository, times(1)).saveAll(List.of(entity));
        }
    }

    @Test
    void testSearchAndSaveRepositories_EmptyResponse() {
        // Arrange
        SearchRequestDto request = new SearchRequestDto();
        request.setQuery("unknown");

        when(gitHubApiClient.searchRepositories(anyString(), any(), any()))
                .thenReturn(new GitHubSearchResponseDto()); // items null

        // Act
        SearchResponseDto result = gitHubService.searchAndSaveRepositories(request);

        // Assert
        assertNotNull(result);
        assertEquals("No repositories found", result.getMessage());
        assertTrue(result.getRepositories().isEmpty());
        verify(repository, never()).saveAll(any());
    }
}
