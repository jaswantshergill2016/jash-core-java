package io.reactivestax.handler;

import javax.persistence.EntityNotFoundException;

public class InvoiceNotFoundException extends EntityNotFoundException {
    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
