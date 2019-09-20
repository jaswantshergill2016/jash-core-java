package io.reactivestax.handler;

import javax.persistence.EntityNotFoundException;

public class ResourceTypeNotFoundException extends EntityNotFoundException {
    public ResourceTypeNotFoundException(String message) {
        super(message);
    }
}
