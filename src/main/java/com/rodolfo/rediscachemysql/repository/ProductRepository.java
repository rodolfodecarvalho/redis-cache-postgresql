package com.rodolfo.rediscachemysql.repository;

import com.rodolfo.rediscachemysql.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}