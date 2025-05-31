package com.rodolfo.rediscachepostgresql.controller;

import com.rodolfo.rediscachepostgresql.dto.request.ProductRequestDto;
import com.rodolfo.rediscachepostgresql.dto.response.ProductResponseDto;
import com.rodolfo.rediscachepostgresql.dto.response.ProductSummaryResponseDto;
import com.rodolfo.rediscachepostgresql.services.ProductServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/products")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductServices productService;

    @GetMapping
    public ResponseEntity<List<ProductSummaryResponseDto>> getAllProducts() {
        log.debug("GET /api/products - Fetching all products");
        List<ProductSummaryResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        log.debug("GET /api/products/{} - Fetching product by id", id);
        ProductResponseDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ProductResponseDto> getProductByCode(@PathVariable String code) {
        log.debug("GET /api/products/code/{} - Fetching product by code", code);
        ProductResponseDto product = productService.getProductByCode(code);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto request) {
        log.debug("POST /api/products - Creating product with code: {}", request.code());
        ProductResponseDto createdProduct = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequestDto request) {
        log.debug("PUT /api/products/{} - Updating product", id);
        ProductResponseDto updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        log.debug("DELETE /api/products/{} - Deleting product", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
