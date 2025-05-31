package com.rodolfo.rediscachepostgresql.services;

import com.rodolfo.rediscachepostgresql.dto.request.ProductRequestDto;
import com.rodolfo.rediscachepostgresql.dto.response.ProductResponseDto;
import com.rodolfo.rediscachepostgresql.dto.response.ProductSummaryResponseDto;
import com.rodolfo.rediscachepostgresql.exeptions.BusinessException;
import com.rodolfo.rediscachepostgresql.exeptions.RecordNotFoundException;
import com.rodolfo.rediscachepostgresql.mapper.ProductMapper;
import com.rodolfo.rediscachepostgresql.model.Product;
import com.rodolfo.rediscachepostgresql.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "products")
@Slf4j
public class ProductServices {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Cacheable(value = "products", key = "'all'", unless = "#result.isEmpty()")
    @Transactional(readOnly = true)
    public List<ProductSummaryResponseDto> getAllProducts() {
        log.debug("Fetching all products from database");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toSummaryResponseDto)
                .toList();
    }

    @Cacheable(value = "products", key = "#id")
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(UUID id) {
        log.debug("Fetching product by id: {}", id);
        Product product = productRepository.findByIdWithComments(id)
                .orElseThrow(() -> new RecordNotFoundException("Product", id.toString()));
        return productMapper.toResponseDto(product);
    }

    @Cacheable(value = "productsByCode", key = "#code")
    @Transactional(readOnly = true)
    public ProductResponseDto getProductByCode(String code) {
        log.debug("Fetching product by code: {}", code);
        Product product = productRepository.findByCode(code)
                .orElseThrow(() -> new RecordNotFoundException("Product", "code", code));
        return productMapper.toResponseDto(product);
    }

    @CacheEvict(value = {"products", "productsByCode"}, allEntries = true)
    public ProductResponseDto createProduct(ProductRequestDto request) {
        log.debug("Creating product with code: {}", request.code());

        if (productRepository.existsByCode(request.code())) {
            throw new BusinessException("Product already exists with code: " + request.code());
        }

        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);

        log.info("Product created with id: {}", savedProduct.getId());
        return productMapper.toResponseDto(savedProduct);
    }

    @CachePut(value = "products", key = "#id")
    @CacheEvict(value = {"products", "productsByCode"}, allEntries = true)
    public ProductResponseDto updateProduct(UUID id, ProductRequestDto request) {
        log.debug("Updating product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Product", id.toString()));

        if (productRepository.existsByCodeAndIdNot(request.code(), id)) {
            throw new BusinessException("Product already exists with code: " + request.code());
        }

        productMapper.updateEntity(product, request);
        Product updatedProduct = productRepository.save(product);

        log.info("Product updated with id: {}", updatedProduct.getId());
        return productMapper.toResponseDto(updatedProduct);
    }

    @CacheEvict(value = {"products", "productsByCode"}, allEntries = true)
    public void deleteProduct(UUID id) {
        log.debug("Deleting product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Product", id.toString()));

        productRepository.delete(product);
        log.info("Product deleted with id: {}", id);
    }
}