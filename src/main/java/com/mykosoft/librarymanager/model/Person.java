package com.mykosoft.librarymanager.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by oleh on 28.12.16.
 */
public class Person {
    @Getter
    @Setter
    protected String firstName;
    @Getter
    @Setter
    protected String lastName;
    @Getter
    @Setter
    protected String middleName;

    public Person() {
    }

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
