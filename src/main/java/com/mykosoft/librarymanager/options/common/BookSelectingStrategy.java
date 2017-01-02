package com.mykosoft.librarymanager.options.common;

import com.mykosoft.librarymanager.model.Book;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by oleh on 02.01.17.
 */
public interface BookSelectingStrategy {
    public Book selectBookFromCollection(Collection<Book> books) throws IOException;
}
