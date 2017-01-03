package com.mykosoft.librarymanager.input.validators;

/**
 * Created by oleh on 03.01.17.
 */
public interface InputValidator<T> {
    public void validateValue(T valueToFormat);
}
