package com.mykosoft.librarymanager.input.validators;

import com.mykosoft.librarymanager.model.Author;
import com.mykosoft.librarymanager.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.valid4j.Validation.*;

/**
 * Created by oleh on 03.01.17.
 */
@Component
public class BookValidator implements InputValidator<Book> {
    @Autowired
    private AuthorValidator authorValidator;

    public static final int MAX_TITLE_LENGTH = 4;
    public static final String TITLE_REGEX = "(.){1," + MAX_TITLE_LENGTH + "}";

    @Override
    public void validateValue(Book book) {
        validate(book.getTitle().matches(TITLE_REGEX), new IllegalArgumentException("Not suitable title"));

        for(Author author:book.getAuthors()){
            authorValidator.validateValue(author);
        }
    }
}
