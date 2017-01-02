package com.mykosoft.librarymanager.options;

/**
 * Created by oleh on 01.01.17.
 */

/**
 * This enum represents possible
 */
public enum Options {
    ADD("add - to add a book"),
    REMOVE("remove - to remove a book"),
    EDIT("edit - to edit a book"),
    ALL("all - to show all books"),
    EXIT("exit -to terminate the program");

    private Options(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
