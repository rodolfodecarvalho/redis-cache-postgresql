package com.rodolfo.rediscachepostgresql.mapper;

import com.rodolfo.rediscachepostgresql.dto.request.ProductRequestDto;
import com.rodolfo.rediscachepostgresql.dto.response.CommentResponseDto;
import com.rodolfo.rediscachepostgresql.dto.response.ProductResponseDto;
import com.rodolfo.rediscachepostgresql.dto.response.ProductSummaryResponseDto;
import com.rodolfo.rediscachepostgresql.model.Comment;
import com.rodolfo.rediscachepostgresql.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.name())
                .code(dto.code())
                .quantity(dto.quantity())
                .price(dto.price())
                .build();
    }

    public ProductResponseDto toResponseDto(Product product) {
        List<CommentResponseDto> comments = product.getComments().stream()
                .map(this::toCommentResponseDto)
                .toList();

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getCode(),
                product.getQuantity(),
                product.getPrice(),
                comments
        );
    }

    public ProductSummaryResponseDto toSummaryResponseDto(Product product) {
        return new ProductSummaryResponseDto(
                product.getId(),
                product.getName(),
                product.getCode(),
                product.getQuantity(),
                product.getPrice(),
                product.getComments().size()
        );
    }

    public CommentResponseDto toCommentResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent()
        );
    }

    public void updateEntity(Product product, ProductRequestDto dto) {
        product.setName(dto.name());
        product.setCode(dto.code());
        product.setQuantity(dto.quantity());
        product.setPrice(dto.price());
    }
}