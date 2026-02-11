package com.example.githubsearcher.exception;

/**
 * Custom runtime exception used to represent errors
 * occurring during communication with the GitHub API.
 *
 * <p>
 * This exception is thrown by {@code GitHubApiClient}
 * when:
 * <ul>
 *     <li>GitHub rate limit is exceeded (HTTP 429)</li>
 *     <li>Client-side errors occur (4xx responses)</li>
 *     <li>Server-side errors occur (5xx responses)</li>
 *     <li>Unexpected API call failures occur</li>
 * </ul>
 * </p>
 *
 * <p>
 * The exception is handled globally by {@code GlobalExceptionHandler},
 * which converts it into an appropriate HTTP response
 * (typically {@code 502 Bad Gateway}).
 * </p>
 *
 * <p>
 * Extends {@link RuntimeException} so it can propagate
 * through the service layer without mandatory catching.
 * </p>
 */
public class GitHubApiException extends RuntimeException {

    /**
     * Constructs a new GitHubApiException with a detail message.
     *
     * @param message Description of the API error
     */
    public GitHubApiException(String message) {
        super(message);
    }

    /**
     * Constructs a new GitHubApiException with a detail message
     * and underlying cause.
     *
     * @param message Description of the API error
     * @param cause   Root cause of the failure
     */
    public GitHubApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
