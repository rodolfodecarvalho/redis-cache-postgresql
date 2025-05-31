package com.rodolfo.rediscachepostgresql.repository;

import com.rodolfo.rediscachepostgresql.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByCode(String code);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.comments WHERE p.id = :id")
    Optional<Product> findByIdWithComments(UUID id);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);
}