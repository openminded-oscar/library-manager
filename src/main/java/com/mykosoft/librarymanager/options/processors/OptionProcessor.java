package com.mykosoft.librarymanager.options.processors;

/**
 * Created by oleh on 01.01.17.
 */
public interface OptionProcessor {
    public void executeCommand();
    public boolean isTerminating();
}
