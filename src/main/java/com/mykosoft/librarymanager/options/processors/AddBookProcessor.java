package com.mykosoft.librarymanager.options.processors;

import com.mykosoft.librarymanager.input.ConsoleReader;
import com.mykosoft.librarymanager.input.validators.BookValidator;
import com.mykosoft.librarymanager.model.Book;
import com.mykosoft.librarymanager.service.BookCrudManager;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.IllegalFormatException;

/**
 * Created by oleh on 01.01.17.
 */
@Component
@Log4j
public class AddBookProcessor implements OptionProcessor {
    @Autowired
    private BookValidator bookValidator;
    @Autowired
    private BookCrudManager bookCrudManager;
    private static ConsoleReader reader = new ConsoleReader();

    @Override
    public void executeCommand() {
        try {
            Book book = reader.readBookObject();
            bookValidator.validateValue(book);
            bookCrudManager.addBook(book);
        } catch (IOException e) {
            System.out.println("Couldn't input a book value");
            log.error(e);
        } catch (IllegalArgumentException e) {
            String msg = "Book not added due to illegal input format.";
            System.out.println(msg + " " + e.getMessage());
            log.error(e);
        }
    }

    @Override
    public boolean isTerminating() {
        return false;
    }
}
