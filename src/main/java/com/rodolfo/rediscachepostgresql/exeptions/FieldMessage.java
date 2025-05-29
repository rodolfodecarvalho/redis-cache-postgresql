package com.rodolfo.rediscachepostgresql.exeptions;

public record FieldMessage(
        String fieldName,

        String message) {
}