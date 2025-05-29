package com.rodolfo.rediscachemysql.dtos;

import com.rodolfo.rediscachemysql.model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(

       @NotNull(message = "Required field") String name,
       @NotNull(message = "Required field") String code,
       @Min(1)int quantity,
       @Min(1)double price
) {

    public Product toEntity() {
        return new Product(null, name, code, quantity, price);
    }
}