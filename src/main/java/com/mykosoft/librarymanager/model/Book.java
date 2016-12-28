package com.mykosoft.librarymanager.model;


import lombok.Getter;

import java.util.Set;

/**
 * Created by oleh on 28.12.16.
 */
public class Book {
    @Getter
    private String title;
    @Getter
    private Set<Author> authors;

    public Book(String title, Set<Author> authors) {
        this.title = title;
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!title.equals(book.title)) return false;
        return authors != null ? authors.equals(book.authors) : book.authors == null;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author=" + authors +
                '}';
    }
}
