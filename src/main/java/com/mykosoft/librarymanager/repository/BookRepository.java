package com.mykosoft.librarymanager.repository;

import com.mykosoft.librarymanager.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by oleh on 28.12.16.
 */
@Component
@Log4j
public class BookRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addNewBook(Book book) {

    }

    public Set<Book> findAllBooks() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT b.id, b.title, ");
        sb.append("a.id, a.author_id, a.first_name, a.last_name, a.middle_name ");
        sb.append("FROM book b INNER JOIN book_author INNER JOIN author a");
        String query = sb.toString();

        log.info(jdbcTemplate.queryForList(query));

        return  new HashSet<>();
    }
}
