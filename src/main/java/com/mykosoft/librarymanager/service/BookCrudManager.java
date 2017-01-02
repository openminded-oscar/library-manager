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

    public Set<Book> findBookByExactTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }

    public void addBook(Book book) {
        bookRepository.addBook(book);
    }

    public void updateBookTitle(Book book){
        bookRepository.updateBookTitle(book);
    }

    public void deleteBookTitle(Book book){
        bookRepository.deleteBook(book);
    }
}
