package com.mykosoft.librarymanager.input.formatters;

/**
 * Created by oleh on 02.01.17.
 */
public interface InputFormatter<T> {
    public T formatValue(T valueToFormat);
}
