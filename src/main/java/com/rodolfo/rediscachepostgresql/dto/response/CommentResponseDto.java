package com.rodolfo.rediscachepostgresql.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public record CommentResponseDto(
        UUID id,
        String name
) {
}
