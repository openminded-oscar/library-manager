package com.mykosoft.librarymanager.input.formatters;

import com.mykosoft.librarymanager.model.Author;

/**
 * Created by oleh on 02.01.17.
 */
public class AuthorFormatter implements InputFormatter<Author> {
    @Override
    public Author formatValue(Author author) {
        if(author.getFirstName().equals("")){
            author.setFirstName(null);
        }

        if(author.getLastName().equals("")){
            author.setLastName(null);
        }

        if(author.getMiddleName().equals("")){
            author.setMiddleName(null);
        }

        return author;
    }
}
