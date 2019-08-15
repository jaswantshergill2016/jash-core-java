package io.reactivestax.commons.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Container class for holding the URI and URLs requested by calling clients.
 *
 */
@Getter
@Setter
public class RequestedResource {

	private String requestUri;
	
	private String requestUrl;
}
