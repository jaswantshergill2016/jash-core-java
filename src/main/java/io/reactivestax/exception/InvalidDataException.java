package io.reactivestax.exception;

import java.util.Arrays;

import io.reactivestax.commons.utils.MessageUtils;

/**
 * This class represents all cases of invalidData - be it in inputDataValidation failure
 * or business rules validation failure.
 */
public class InvalidDataException extends AppRuntimeException {

    public InvalidDataException() {
        super();
    }

	public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(Throwable cause) {
        super(cause);
    }
    @Override
   	public String toString() {
   		return "InvalidDataException [ getErrorMap()=" + MessageUtils.printMapInJSONFormat(getErrorMap())
   				+ ", getMessage()="
   				+ getMessage() + ", getCause()=" + getCause() + ", getStackTrace()=" + Arrays.toString(getStackTrace())
   				+ "]";
   	}
}