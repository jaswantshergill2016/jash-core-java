package io.reactivestax.api.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestApiResponse<RestApiStatus, R> {

    private RestApiStatus status;
    private R response;

}
