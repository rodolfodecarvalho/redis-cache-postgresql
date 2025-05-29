package com.rodolfo.rediscachemysql.exeptions;

public record FieldMessage(
        String fieldName,

        String message) {
}