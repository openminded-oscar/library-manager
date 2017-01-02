package com.mykosoft.librarymanager.options.processors;

import com.mykosoft.librarymanager.model.Book;
import com.mykosoft.librarymanager.service.BookCrudManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by oleh on 02.01.17.
 */
@Component
public class RemoveBookProcessor implements OptionProcessor {
    @Autowired
    private BookCrudManager bookCrudManager;


    @Override
    public void executeCommand() {
        Book book = null;
        bookCrudManager.deleteBook(book);
    }

    @Override
    public boolean isTerminating() {
        return false;
    }
}