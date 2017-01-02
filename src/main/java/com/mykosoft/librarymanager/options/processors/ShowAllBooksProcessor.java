package com.mykosoft.librarymanager.options.processors;

import com.mykosoft.librarymanager.model.Book;
import com.mykosoft.librarymanager.service.BookCrudManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by oleh on 01.01.17.
 */
@Component
public class ShowAllBooksProcessor implements OptionProcessor {
    @Autowired
    private BookCrudManager bookCrudManager;

    @Override
    public void executeCommand() {
        Set<Book> allBooks = bookCrudManager.findAllBooks();

        System.out.println(allBooks);
    }

    @Override
    public boolean isTerminating() {
        return false;
    }
}
