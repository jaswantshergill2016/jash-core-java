package io.reactivestax.handler;

import javax.persistence.EntityNotFoundException;

public class ExpenseTypeNotFoundException extends EntityNotFoundException {
    public ExpenseTypeNotFoundException(String message) {
        super(message);
    }
}
