package com.example.githubsearcher.client;

import com.example.githubsearcher.dto.github.GitHubSearchResponseDto;
import com.example.githubsearcher.exception.GitHubApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GitHubApiClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec uriSpec;

    @Mock
    private WebClient.RequestHeadersSpec headersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private GitHubApiClient gitHubApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void mockSuccessFlow(GitHubSearchResponseDto dto) {

        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(any(Function.class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(GitHubSearchResponseDto.class))
                .thenReturn(Mono.just(dto));
    }

    @Test
    void searchRepositories_success() {

        GitHubSearchResponseDto dto =
                new GitHubSearchResponseDto(Collections.emptyList());

        mockSuccessFlow(dto);

        GitHubSearchResponseDto result =
                gitHubApiClient.searchRepositories("spring", "Java", "stars");

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void searchRepositories_withoutLanguage() {

        GitHubSearchResponseDto dto =
                new GitHubSearchResponseDto(Collections.emptyList());

        mockSuccessFlow(dto);

        GitHubSearchResponseDto result =
                gitHubApiClient.searchRepositories("spring", null, null);

        assertNotNull(result);
    }

    @Test
    void searchRepositories_rateLimitException() {

        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(any(Function.class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.onStatus(any(), any()))
                .thenThrow(new GitHubApiException("GitHub API rate limit exceeded"));

        assertThrows(GitHubApiException.class, () ->
                gitHubApiClient.searchRepositories("spring", null, null)
        );
    }

    @Test
    void searchRepositories_webClientResponseException() {

        when(webClient.get()).thenReturn(uriSpec);

        when(uriSpec.uri(any(Function.class)))
                .thenThrow(WebClientResponseException.create(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        null,
                        null,
                        StandardCharsets.UTF_8
                ));

        GitHubApiException exception = assertThrows(GitHubApiException.class, () ->
                gitHubApiClient.searchRepositories("spring", null, null)
        );

        assertTrue(exception.getMessage().contains("Error calling GitHub API"));
    }

    @Test
    void searchRepositories_blankLanguage() {

        GitHubSearchResponseDto dto =
                new GitHubSearchResponseDto(Collections.emptyList());

        mockSuccessFlow(dto);

        GitHubSearchResponseDto result =
                gitHubApiClient.searchRepositories("spring boot", "", null);

        assertNotNull(result);
    }
}
