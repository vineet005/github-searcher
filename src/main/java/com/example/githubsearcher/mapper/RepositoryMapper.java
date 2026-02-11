package com.example.githubsearcher.mapper;

import com.example.githubsearcher.dto.RepositoryResponseDto;
import com.example.githubsearcher.dto.github.GitHubRepositoryDto;
import com.example.githubsearcher.entity.RepositoryEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility mapper class responsible for converting between:
 *
 * <ul>
 *     <li>GitHub API DTOs → Database Entities</li>
 *     <li>Database Entities → API Response DTOs</li>
 * </ul>
 *
 * <p>
 * This class ensures clean separation of concerns by preventing
 * direct coupling between:
 * <ul>
 *     <li>External GitHub API models</li>
 *     <li>Internal persistence models</li>
 *     <li>Client-facing response models</li>
 * </ul>
 * </p>
 *
 * <p>
 * Annotated with {@link UtilityClass} to enforce:
 * <ul>
 *     <li>Static methods only</li>
 *     <li>No instantiation</li>
 * </ul>
 * </p>
 */
@UtilityClass
public class RepositoryMapper {

    /**
     * Converts a GitHub API repository DTO into a database entity.
     *
     * @param dto GitHubRepositoryDto received from GitHub API
     * @return RepositoryEntity ready for persistence
     */
    public static RepositoryEntity toEntity(GitHubRepositoryDto dto) {

        if (dto == null) {
            return null;
        }

        return RepositoryEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .owner(
                        dto.getOwner() != null
                                ? dto.getOwner().getLogin()
                                : null
                )
                .language(dto.getLanguage())
                .stars(dto.getStars())
                .forks(dto.getForks())
                .lastUpdated(dto.getUpdatedAt())
                .build();
    }

    /**
     * Converts a database entity into a client-facing response DTO.
     *
     * @param entity RepositoryEntity from database
     * @return RepositoryResponseDto for API response
     */
    public static RepositoryResponseDto toResponseDto(RepositoryEntity entity) {

        if (entity == null) {
            return null;
        }

        return RepositoryResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .owner(entity.getOwner())
                .language(entity.getLanguage())
                .stars(entity.getStars())
                .forks(entity.getForks())
                .lastUpdated(entity.getLastUpdated())
                .build();
    }

    /**
     * Converts a list of GitHub repository DTOs into
     * a list of database entities.
     *
     * <p>
     * Null-safe: returns an empty list if input is null.
     * Filters out null elements before mapping.
     * </p>
     *
     * @param dtoList List of GitHubRepositoryDto objects
     * @return List of RepositoryEntity objects
     */
    public static List<RepositoryEntity> toEntityList(List<GitHubRepositoryDto> dtoList) {

        if (dtoList == null) {
            return List.of();
        }

        return dtoList.stream()
                .filter(Objects::nonNull)
                .map(RepositoryMapper::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of database entities into
     * a list of response DTOs.
     *
     * <p>
     * Null-safe: returns an empty list if input is null.
     * Filters out null elements before mapping.
     * </p>
     *
     * @param entities List of RepositoryEntity objects
     * @return List of RepositoryResponseDto objects
     */
    public static List<RepositoryResponseDto> toResponseDtoList(List<RepositoryEntity> entities) {

        if (entities == null) {
            return List.of();
        }

        return entities.stream()
                .filter(Objects::nonNull)
                .map(RepositoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
