package com.mykosoft.librarymanager.options.processors;

import org.springframework.stereotype.Component;

/**
 * Created by oleh on 01.01.17.
 */
@Component
public class ExitProcessor implements OptionProcessor {
    @Override
    public void executeCommand() {
        System.out.println("Stopping the application...");
    }

    @Override
    public boolean isTerminating() {
        return true;
    }

    @Override
    public String toString() {
        return "ExitProcessor{}";
    }
}
