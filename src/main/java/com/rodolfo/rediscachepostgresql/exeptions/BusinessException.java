package com.rodolfo.rediscachepostgresql.exeptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
