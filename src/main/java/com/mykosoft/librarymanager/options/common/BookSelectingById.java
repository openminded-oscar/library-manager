package com.mykosoft.librarymanager.options.common;

import com.mykosoft.librarymanager.input.ConsoleReader;
import com.mykosoft.librarymanager.model.Book;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by oleh on 02.01.17.
 */
@Component
public class BookSelectingById implements BookSelectingStrategy {
    private static ConsoleReader reader = new ConsoleReader();

    @Override
    public Book selectBookFromCollection(Collection<Book> booksByTitle) throws IOException {
        Book book = null;

        if (booksByTitle.size() > 1) {
            System.out.println("Multiple books exist with that title!");
            for (Book bookByTitle : booksByTitle) {
                System.out.println(bookByTitle);
            }
            System.out.println("Choose the book by it's id!");
            Integer selectedId = reader.readInteger("Input book id to select it:");
            book = booksByTitle.stream().filter(b -> b.getId().equals(selectedId)).findAny().get();
        } else {
            book = booksByTitle.iterator().next();
            reader.readLine("One book found\n" + book.toString());
        }

        return book;
    }
}
