package io.reactivestax.constants;

public enum ApiStatus {

    NOT_READABLE_REQUEST_PAYLOAD(400,"Invalid request payload"),
    INVALID_OR_BAD_REQUEST(400,"Invalid OR Bad equest attributes"),

    SYSTEM_UNABLE_TO_PROCESS_REQUEST(500,"System unable to process the request"),

    OK(200,"Request processed successfully"),

    RATES_CONVERSION_REQUEST_PROCESSED(200,"Rates conversion request processed successfully"),

    CURRENCYCONVERSION_REQUEST_PROCESSED(200,"Currency conversion request processed successfully"),


    ;

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
