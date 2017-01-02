package com.mykosoft.librarymanager.options.processors;

import com.mykosoft.librarymanager.ConsoleReader;
import com.mykosoft.librarymanager.model.Book;
import com.mykosoft.librarymanager.service.BookCrudManager;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

/**
 * Created by oleh on 02.01.17.
 */
@Component
@Log4j
public class EditBookProcessor implements OptionProcessor {
    @Autowired
    private BookCrudManager bookCrudManager;
    private static ConsoleReader reader = new ConsoleReader();


    @Override
    public void executeCommand() {
        String bookTitle = null;
        Book book = null;

        try {
            bookTitle = reader.readLine("Input book title or it's fragment to edit:");

            Set<Book> booksByTitle = bookCrudManager.findBookByTitle(bookTitle);
            if (booksByTitle.size() >= 1) {
                if (booksByTitle.size() > 1) {
                    System.out.println("Multiple books exist with that title!");
                    for(Book bookByTitle: booksByTitle){
                        System.out.println(bookByTitle);
                    }
                    System.out.println("Choose the book by it's id!");
                    Integer selectedId = reader.readInteger("Input book id to select it:");
                    book = booksByTitle.stream().filter(b -> b.getId().equals(selectedId)).findAny().get();
                } else {
                    book = booksByTitle.iterator().next();
                    System.out.println(book);
                }
                String newTitle = reader.readLine("Input book new title:");
                bookCrudManager.updateBookTitle(book, newTitle);
            } else {
                System.out.println("No books found with that title");
            }
        } catch (IOException e) {
            System.out.println("Failed on user input");
        }
    }

    @Override
    public boolean isTerminating() {
        return false;
    }
}
