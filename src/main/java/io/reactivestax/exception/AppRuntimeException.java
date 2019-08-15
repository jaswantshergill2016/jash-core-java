package io.reactivestax.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.reactivestax.commons.utils.MessageUtils;

/**
 * This is the main unchecked exception that all APPs should be throwing for any foreseen system related issues.
 * - which don't fit into data related issues.
 */
public class AppRuntimeException extends RuntimeException {

	private final Map<String, String> errorMap;

	public AppRuntimeException() {
		super();
		this.errorMap = new HashMap<>();
	}


	public AppRuntimeException(Map<String, String> errorMap) {
		super();
		this.errorMap = errorMap;
	}


	public AppRuntimeException(Map<String, String> errorMap, Throwable cause) {
		super(cause);
		this.errorMap = errorMap;
	}


	public AppRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.errorMap = new HashMap<>();
	}

	public AppRuntimeException(String message) {
		super(message);
		this.errorMap = new HashMap<>();
	}

	public AppRuntimeException(Throwable cause) {
		super(cause);
		this.errorMap = new HashMap<>();
	}


	public Map<String, String> getErrorMap() {
		return errorMap;
	}
	
	@Override
   	public String toString() {
   		return "AppRuntimeException [ getErrorMap()=" + MessageUtils.printMapInJSONFormat(getErrorMap())
   				+ ", getMessage()="
   				+ getMessage() + ", getCause()=" + getCause() + ", getStackTrace()=" + Arrays.toString(getStackTrace())
   				+ "]";
   	}
}