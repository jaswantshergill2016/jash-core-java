package io.reactivestax.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Request metadata needs to be populated for each requst
 */

@Data
public class RequestMetadata implements Serializable {

    private String requestId;

    private Long requestTime;

    private String language;

    private boolean generateExceptionTrace;
}
