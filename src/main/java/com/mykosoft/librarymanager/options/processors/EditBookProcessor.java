package com.mykosoft.librarymanager.options.processors;

import com.mykosoft.librarymanager.input.ConsoleReader;
import com.mykosoft.librarymanager.input.validators.BookValidator;
import com.mykosoft.librarymanager.model.Book;
import com.mykosoft.librarymanager.options.common.BookSelectingStrategy;
import com.mykosoft.librarymanager.service.BookCrudManager;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Set;

import static org.valid4j.Validation.validate;

/**
 * Created by oleh on 02.01.17.
 */
@Component
@Log4j
public class EditBookProcessor implements OptionProcessor {
    @Autowired
    private BookCrudManager bookCrudManager;
    @Autowired
    private BookSelectingStrategy bookSelector;

    private static ConsoleReader reader = new ConsoleReader();


    @Override
    public void executeCommand() {
        String bookTitle = null;
        Book book = null;

        try {
            bookTitle = reader.readLine("Input book title or it's fragment to edit:");

            Set<Book> booksByTitle = bookCrudManager.findBookByTitle(bookTitle);
            if (booksByTitle.size() >= 1) {
                book = bookSelector.selectBookFromCollection(booksByTitle);
                String newTitle = reader.readLine("Input book new title:");
                validate(newTitle.matches(BookValidator.TITLE_REGEX),
                        new IllegalArgumentException("Not suitable title!"));
                bookCrudManager.updateBookTitle(book, newTitle);
            } else {
                System.out.println("No books found with that title");
            }
        } catch (IOException e) {
            String msg = "Updating failed on user input";
            System.out.println(msg);
            log.error(msg);
        }
        catch(IllegalArgumentException e){
            System.out.println("Invalid user input! "+ e.getMessage());
        }
    }

    @Override
    public boolean isTerminating() {
        return false;
    }
}
