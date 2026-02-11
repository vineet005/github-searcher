package com.example.githubsearcher.client;

import com.example.githubsearcher.dto.github.GitHubSearchResponseDto;
import com.example.githubsearcher.exception.GitHubApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * Client component responsible for communicating with the
 * GitHub REST API.
 *
 * <p>
 * This class uses Spring's {@link WebClient} to call the
 * GitHub Search Repositories endpoint:
 * {@code https://api.github.com/search/repositories}
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 *     <li>Build GitHub search queries</li>
 *     <li>Invoke external API</li>
 *     <li>Handle HTTP errors and rate limiting</li>
 *     <li>Deserialize response into {@link GitHubSearchResponseDto}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Note: Although {@link WebClient} is reactive, this application
 * operates in a synchronous (blocking) manner. Therefore,
 * {@code .block()} is used to retrieve the response.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class GitHubApiClient {

    /**
     * Pre-configured WebClient bean for GitHub API communication.
     * Configured in {@code WebClientConfig}.
     */
    private final WebClient gitHubWebClient;

    /**
     * Calls the GitHub Search API to fetch repositories
     * based on the provided search criteria.
     *
     * @param query     Search keyword or phrase (required)
     * @param language  Optional programming language filter
     * @param sort      Optional sorting parameter (stars, forks, updated)
     * @return GitHubSearchResponseDto containing repository search results
     * @throws GitHubApiException if API call fails or rate limit is exceeded
     */
    public GitHubSearchResponseDto searchRepositories(
            String query,
            String language,
            String sort
    ) {

        String finalQuery = buildQuery(query, language);

        try {
            return gitHubWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search/repositories")
                            .queryParam("q", finalQuery)
                            .queryParamIfPresent("sort",
                                    sort != null
                                            ? java.util.Optional.of(sort)
                                            : java.util.Optional.empty())
                            .build()
                    )
                    .retrieve()

                    // Handle GitHub rate limiting (HTTP 429)
                    .onStatus(HttpStatus.TOO_MANY_REQUESTS::equals,
                            response -> Mono.error(
                                    new GitHubApiException("GitHub API rate limit exceeded")))

                    // Handle other 4xx client errors
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> Mono.error(
                                    new GitHubApiException("GitHub API client error")))

                    // Handle 5xx server errors
                    .onStatus(HttpStatusCode::is5xxServerError,
                            response -> Mono.error(
                                    new GitHubApiException("GitHub API server error")))

                    .bodyToMono(GitHubSearchResponseDto.class)
                    .block(); // Blocking call since application is not reactive

        } catch (WebClientResponseException ex) {
            throw new GitHubApiException(
                    "Error calling GitHub API: " + ex.getStatusCode(),
                    ex
            );
        }
    }

    /**
     * Builds the GitHub search query string.
     *
     * <p>
     * If language is provided, it appends the filter
     * in GitHub's expected format:
     * </p>
     *
     * <pre>
     * Example:
     * query = "spring boot"
     * language = "Java"
     *
     * Result:
     * "spring boot language:Java"
     * </pre>
     *
     * @param query     Search keyword
     * @param language  Optional programming language
     * @return Formatted GitHub query string
     */
    private String buildQuery(String query, String language) {

        StringBuilder builder = new StringBuilder(query);

        if (language != null && !language.isBlank()) {
            builder.append(" language:").append(language);
        }

        return builder.toString();
    }
}
