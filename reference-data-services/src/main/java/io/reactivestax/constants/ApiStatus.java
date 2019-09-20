package io.reactivestax.constants;

public enum ApiStatus {

    NOT_READABLE_REQUEST_PAYLOAD(400,"Invalid request payload"),
    INVALID_PAYLOAD(400,"Invalid request payload"),

    RESOURCE_NOT_FOUND(404,"Request resource not found"),
    SYSTEM_UNABLE_TO_PROCESS_REQUEST(500,"System unable to process the request"),

    USER_API_SUCCESS(200,"OK"),
    USER_CREATED(201,"OK");


    private int statusCode;

    private String statusDesc;

    ApiStatus(int statusCode, String statusDesc) {
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }
}
