package com.rodolfo.rediscachepostgresql.exeptions;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(long id) {
		super("Record not found with id: " + id);
	}
}