package com.rodolfo.rediscachemysql.controller;

import com.rodolfo.rediscachemysql.dtos.ProductDTO;
import com.rodolfo.rediscachemysql.model.Product;
import com.rodolfo.rediscachemysql.services.ProductServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/products")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductServices productServices;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<Product> getAll() {
        return productServices.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Product getById(@PathVariable Long id) {
        log.info("method=getById, step=starting, id={}", id);
        return productServices.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Product create(@RequestBody @Valid ProductDTO productDTO) {
        log.info("method=create, step=starting, productDTO={}", productDTO);
        return productServices.save(productDTO.toEntity());
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Product update(@PathVariable Long id, @RequestBody Product productDetails) {
        return productServices.update(id, productDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        productServices.deleteById(id);
    }
}
