package io.reactivestax.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorsView {

    private List<ApiFieldError> fieldErrors = new ArrayList<>();

}
