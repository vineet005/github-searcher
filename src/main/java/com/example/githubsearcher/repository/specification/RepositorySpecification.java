package com.example.githubsearcher.repository.specification;

import com.example.githubsearcher.entity.RepositoryEntity;
import org.springframework.data.jpa.domain.Specification;

/**
 * Utility class containing JPA Specifications for
 * dynamic filtering of {@link RepositoryEntity}.
 *
 * <p>
 * Specifications allow building type-safe, dynamic queries
 * at runtime without writing explicit JPQL or SQL.
 * </p>
 *
 * <p>
 * These specifications are used in combination with
 * {@link org.springframework.data.jpa.repository.JpaSpecificationExecutor}
 * in the repository layer.
 * </p>
 *
 * <p>
 * Each method returns a {@link Specification} that can be
 * combined using:
 * <ul>
 *     <li>Specification.where()</li>
 *     <li>.and()</li>
 *     <li>.or()</li>
 * </ul>
 * </p>
 */
public class RepositorySpecification {

    /**
     * Filters repositories by programming language.
     *
     * <p>
     * If language is null or blank, no filtering is applied.
     * </p>
     *
     * @param language Programming language to filter by
     * @return Specification for language filter
     */
    public static Specification<RepositoryEntity> hasLanguage(String language) {
        return (root, query, cb) ->
                language == null || language.isBlank()
                        ? null
                        : cb.equal(root.get("language"), language);
    }

    /**
     * Filters repositories by minimum star count.
     *
     * <p>
     * If minStars is null, no filtering is applied.
     * </p>
     *
     * @param minStars Minimum number of stars required
     * @return Specification for star count filter
     */
    public static Specification<RepositoryEntity> hasMinStars(Integer minStars) {
        return (root, query, cb) ->
                minStars == null
                        ? null
                        : cb.greaterThanOrEqualTo(root.get("stars"), minStars);
    }
}
