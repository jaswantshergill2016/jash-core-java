package io.reactivestax.handler;

import javax.persistence.EntityNotFoundException;

public class VendorNotFoundException extends EntityNotFoundException {
    public VendorNotFoundException(String message) {
        super(message);
    }
}
