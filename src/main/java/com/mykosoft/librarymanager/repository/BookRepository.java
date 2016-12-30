package com.mykosoft.librarymanager.repository;

import com.mykosoft.librarymanager.model.Author;
import com.mykosoft.librarymanager.model.Book;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
        Set<Book> books = null;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("b.id as b_id, ");
        sb.append("b.title as b_title, ");
        sb.append("a.id as a_id, ");
        sb.append("a.author_id as a_author_id, ");
        sb.append("a.first_name as a_first_name, ");
        sb.append("a.last_name as a_last_name, ");
        sb.append("a.middle_name as a_middle_name ");
        sb.append("FROM book b INNER JOIN book_author INNER JOIN author a ORDER BY b_id");

        String query = sb.toString();
        books = jdbcTemplate.query(query, new BookSetMapper());
        log.info(books);

        return books;
    }

    public class BookSetMapper implements ResultSetExtractor<Set<Book>> {
        @Override
        public Set<Book> extractData(ResultSet rs) throws SQLException {
            Map<Integer, Book> books = new HashMap<>();
            Map<Integer, Author> authors = new HashMap<>();

            while (rs.next()) {
                Integer authorTableId = rs.getInt("a_id");
                Integer bookTableId = rs.getInt("b_id");

                Author author = authors.get(authorTableId);
                Book book = books.get(bookTableId);

                if (author == null) {
                    author = new Author(rs.getString("a_first_name"),
                            rs.getString("a_last_name"),
                            rs.getString("a_middle_name"),
                            rs.getInt("a_author_id"));
                    authors.put(authorTableId, author);
                }

                if (books.get(bookTableId) == null) {
                    book = new Book(rs.getString("b_title"), new HashSet<>());
                    books.put(bookTableId, book);
                }

                book.getAuthors().add(author);
            }

            Set<Book> bookSet = new HashSet<>(books.values());

            return bookSet;
        }
    }
}