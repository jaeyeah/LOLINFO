package com.lol.lolinfo.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotEnoughHeartException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotEnoughHeartException(String message) {
        super(message);
    }
}
