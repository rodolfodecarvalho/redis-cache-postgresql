package com.rodolfo.rediscachepostgresql.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequestDto(
        @NotBlank(message = "Content is required")
        String content
) {
}
