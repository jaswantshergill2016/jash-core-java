package io.reactivestax.commons.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;


@Getter
@Setter
public class RequestMetadata {

	@NotBlank(message = "requestUniqueId in request_metadata request header is mandatory")
	private String requestUniqueId;

	@NotBlank(message = "requestTime in request-metadata request header is mandatory")
	private String requestTime;

	private boolean generateExceptionTrace  = false;

	private String ipAddress;

	private String language;

}
