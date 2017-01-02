package com.mykosoft.librarymanager.service;

import com.mykosoft.librarymanager.model.Book;
import com.mykosoft.librarymanager.repository.BookRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by oleh on 28.12.16.
 */
@Component
public class BookCrudManager {
    @Autowired
    @Getter
    private BookRepository bookRepository;

    public Set<Book> findAllBooks() {
        return bookRepository.findAllBooks();
    }

    // returns books by title like matching
    public Set<Book> findBookByTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }

    public void addBook(Book book) {
        bookRepository.addBook(book);
    }

    public void updateBookTitle(Book book, String newTitle) {
        bookRepository.updateBookTitle(book, newTitle);
    }

    public void deleteBook(Book book) {
        bookRepository.deleteBook(book);
    }
}
