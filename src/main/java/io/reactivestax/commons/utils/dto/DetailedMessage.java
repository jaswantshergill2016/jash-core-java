package io.reactivestax.commons.utils.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * Represents the detailed message to be conveyed to the calling client.
 * Explains the message, the actual object and its specific field that
 * caused the error
 */
public  class DetailedMessage {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String object;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;

    /**
     * Basic Contructor for DetailedMessage class
     */
    public DetailedMessage() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
