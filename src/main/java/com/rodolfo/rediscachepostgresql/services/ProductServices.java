package com.rodolfo.rediscachepostgresql.services;

import com.rodolfo.rediscachepostgresql.exeptions.RecordNotFoundException;
import com.rodolfo.rediscachepostgresql.model.Product;
import com.rodolfo.rediscachepostgresql.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "product")
@Slf4j
public class ProductServices {

    private final ProductRepository productRepository;

    @Cacheable(value = "allProducts", unless = "#result.isEmpty()")
    public List<Product> findAll() {
        log.info("method=findAll, step=starting");
        return productRepository.findAll();
    }

    @Cacheable(value = "productByID", key = "#id")
    public Product findById(long id) {
        log.info("method=findById, step=starting, id={}", id);
        return productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    //@CachePut(cacheNames = "product", key = "#result.id")
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#result.id"),
            @CacheEvict(value = "allProducts", allEntries = true),
            @CacheEvict(value = "productByID", allEntries = true)
    })
    public Product save(Product product) {
        log.info("method=save, step=starting, product={}", product);
        return productRepository.save(product);
    }

    @CachePut(cacheNames = "product", key = "#id")
    public Product update(long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        productDetails.setCode(productDetails.getCode());
        return productRepository.save(product);
    }

    @CacheEvict(cacheNames = "product", key = "#id", allEntries = true)
    public void deleteById(long id) {
        productRepository.delete(productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }
}