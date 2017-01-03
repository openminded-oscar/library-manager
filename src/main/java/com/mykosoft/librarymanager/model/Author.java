package com.mykosoft.librarymanager.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ValidationUtils;

/**
 * Created by oleh on 28.12.16.
 */
public class Author extends Person {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private Integer authorId;

    public Author(Integer authorId) {
        super();
        this.authorId = authorId;
    }

    public Author(String firstName, String lastName, String middleName, Integer authorId) {
        super(firstName, lastName, middleName);
        
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return authorId.equals(author.authorId);
    }

    @Override
    public int hashCode() {
        return authorId.hashCode();
    }

    @Override
    public String toString() {
        return "Author{" + super.toString() +
                "authorId=" + authorId +
                '}';
    }
}