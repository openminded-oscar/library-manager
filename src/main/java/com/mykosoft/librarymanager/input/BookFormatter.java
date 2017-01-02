package com.mykosoft.librarymanager.input;

import com.mykosoft.librarymanager.model.Author;
import com.mykosoft.librarymanager.model.Book;

import java.util.List;

/**
 * Created by oleh on 02.01.17.
 */
public class BookFormatter implements InputFormatter<Book> {
    private AuthorFormatter authorFormatter = new AuthorFormatter();

    @Override
    public Book formatValue(Book book) {
        if (book.getTitle().equals("")) {
            book.setTitle(null);
        }

        List<Author> authors = book.getAuthors();
        if(authors!=null) {
            for (Author author : authors) {
                authorFormatter.formatValue(author);
            }
        }

        return book;
    }
}
