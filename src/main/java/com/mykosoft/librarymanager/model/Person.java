package com.mykosoft.librarymanager.model;

import lombok.Getter;

/**
 * Created by oleh on 28.12.16.
 */
public class Person {
    @Getter
    protected String firstName;
    @Getter
    protected String lastName;
    @Getter
    protected String middleName;

    public Person(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }
}
