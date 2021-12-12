package org.nailservice.service.validator;

public interface Validator<E> {
    void validate(E entity);
}
