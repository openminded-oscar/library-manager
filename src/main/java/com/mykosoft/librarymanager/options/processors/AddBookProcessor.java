package com.mykosoft.librarymanager.options.processors;

import com.mykosoft.librarymanager.ConsoleReader;
import com.mykosoft.librarymanager.model.Book;
import com.mykosoft.librarymanager.service.BookCrudManager;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by oleh on 01.01.17.
 */
@Component
@Log4j
public class AddBookProcessor implements OptionProcessor {
    @Autowired
    private BookCrudManager bookCrudManager;
    private static ConsoleReader reader = new ConsoleReader();

    @Override
    public void executeCommand() {
        try {
            Book book = reader.readBookObject();
            bookCrudManager.addBook(book);
        } catch (IOException e) {
            System.out.println("Couldn't input a book value");
            log.error(e);
        }
    }

    @Override
    public boolean isTerminating() {
        return false;
    }
}
