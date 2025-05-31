package com.rodolfo.rediscachepostgresql.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public record ProductResponseDto(
        UUID id,
        String name,
        String code,
        Integer quantity,
        BigDecimal price,
        List<CommentResponseDto> comments
) {
}
