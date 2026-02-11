package com.example.githubsearcher.repository;

import com.example.githubsearcher.entity.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations
 * on {@link RepositoryEntity}.
 *
 * <p>
 * Extends:
 * <ul>
 *     <li>{@link JpaRepository} - Provides basic CRUD operations</li>
 *     <li>{@link JpaSpecificationExecutor} - Enables dynamic filtering using Specifications</li>
 * </ul>
 * </p>
 *
 * <p>
 * Key capabilities inherited from JpaRepository:
 * <ul>
 *     <li>save()</li>
 *     <li>saveAll()</li>
 *     <li>findById()</li>
 *     <li>findAll()</li>
 *     <li>deleteById()</li>
 * </ul>
 * </p>
 *
 * <p>
 * JpaSpecificationExecutor allows the service layer to:
 * <ul>
 *     <li>Filter repositories dynamically (e.g., by language)</li>
 *     <li>Apply conditional queries (e.g., minimum stars)</li>
 *     <li>Combine multiple predicates efficiently</li>
 * </ul>
 * </p>
 *
 * <p>
 * This interface contains no implementation code —
 * Spring Data JPA automatically generates the implementation at runtime.
 * </p>
 */
@Repository
public interface RepositoryEntityRepository
        extends JpaRepository<RepositoryEntity, Long>,
        JpaSpecificationExecutor<RepositoryEntity> {

}
