package com.rodolfo.rediscachepostgresql.repository;

import com.rodolfo.rediscachepostgresql.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}