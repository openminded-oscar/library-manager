package com.mykosoft.librarymanager.repository;

import com.mykosoft.librarymanager.model.Author;
import com.mykosoft.librarymanager.model.Book;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by oleh on 28.12.16.
 */
@Component
@Log4j
public class BookRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource ds;

    @Transactional
    public void deleteBook(Book book) {
        String removalQuery = "DELETE FROM tbl_book where id = ?";
        jdbcTemplate.update(removalQuery, book.getId());
    }

    @Transactional
    public void updateBookTitle(Book book, String newTitle) {
        String updateTitleQuery = "UPDATE tbl_book SET title = ? where id = ?";
        jdbcTemplate.update(updateTitleQuery, newTitle, book.getId());
    }

    @Transactional
    public void addBook(Book book) {
        // insert a book
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", book.getTitle());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(ds).withTableName("tbl_book").usingColumns("title")
                .usingGeneratedKeyColumns("id");
        Integer generatedBookId = (Integer) insert.executeAndReturnKey(params);
        book.setId(generatedBookId);

        List<Author> bookAuthors = book.getAuthors();
        if (bookAuthors != null && bookAuthors.size() > 0) {
            // insert not existed in DB related authors
            StringBuilder sb = new StringBuilder("SELECT ");
            sb.append("id as a_id, ");
            sb.append("first_name as a_first_name, ");
            sb.append("last_name as a_last_name, ");
            sb.append("middle_name as a_middle_name, ");
            sb.append("author_id as a_author_id ");
            sb.append("FROM tbl_author WHERE author_id IN (");
            for (Author author : book.getAuthors()) {
                Integer authorId = author.getAuthorId();
                sb.append(authorId + ", ");
            }
            String selectExistingQuery = sb.toString();
            selectExistingQuery = selectExistingQuery.substring(0, selectExistingQuery.length() - 2);
            selectExistingQuery += ")";
            log.info(selectExistingQuery);
            List<Author> alreadyPresentAuthors = jdbcTemplate.query(selectExistingQuery, new RowMapper<Author>() {
                @Override
                public Author mapRow(ResultSet rs, int i) throws SQLException {
                    Author author = extractAuthorFromResultSet(rs);
                    return author;
                }
            });

            List<Author> authorsToInsert = new ArrayList<>();
            for (Author author : bookAuthors) {
                if (!alreadyPresentAuthors.contains(author)) {
                    authorsToInsert.add(author);
                }
            }

            final String authorInsertQuery = "INSERT INTO tbl_author " +
                    "(first_name, last_name, middle_name, author_id)" +
                    " values (?,?,?,?)";

            jdbcTemplate.batchUpdate(authorInsertQuery, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    Author currentAuthor = authorsToInsert.get(i);
                    preparedStatement.setString(1, currentAuthor.getFirstName());
                    preparedStatement.setString(2, currentAuthor.getLastName());
                    preparedStatement.setString(3, currentAuthor.getMiddleName());
                    preparedStatement.setInt(4, currentAuthor.getAuthorId());
                }

                @Override
                public int getBatchSize() {
                    return authorsToInsert.size();
                }
            });

            List<Author> allAuthorsWithIds = jdbcTemplate.query(selectExistingQuery, new RowMapper<Author>() {
                @Override
                public Author mapRow(ResultSet rs, int i) throws SQLException {
                    Author author = extractAuthorFromResultSet(rs);
                    return author;
                }
            });

            // grab authors ids
            jdbcTemplate.batchUpdate("INSERT INTO tbl_book_author (book_id, author_id) values (?,?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            preparedStatement.setLong(1, book.getId());
                            preparedStatement.setLong(2, allAuthorsWithIds.get(i).getId());
                        }

                        @Override
                        public int getBatchSize() {
                            return book.getAuthors().size();
                        }
                    });
        }
    }


    public Set<Book> findAllBooks() {
        return findBooks(null);
    }

    public Set<Book> findBooksByTitle(String title) {
        return findBooks(title);
    }

    private Set<Book> findBooks(String title) {
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
        sb.append("FROM tbl_book b INNER JOIN tbl_book_author ba ");
        sb.append("ON b.id=ba.book_id ");
        sb.append("INNER JOIN tbl_author a ");
        sb.append("ON a.id=ba.author_id ");
        if (title != null) {
            sb.append("WHERE b.title LIKE ?");
        }
        sb.append("ORDER BY b.title");

        String query = sb.toString();
        System.out.println(query);
        if (title != null) {
            Object[] parameters = {"%" + title + "%"};
            books = jdbcTemplate.query(query, parameters, new BookSetMapper());
        } else {
            books = jdbcTemplate.query(query, new BookSetMapper());
        }

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
                    author = extractAuthorFromResultSet(rs);
                    authors.put(authorTableId, author);
                }

                if (books.get(bookTableId) == null) {
                    book = new Book(rs.getString("b_title"), new ArrayList<>());
                    book.setId(bookTableId);
                    books.put(bookTableId, book);
                }

                System.out.println("Author added " + author);
                book.getAuthors().add(author);
            }

            Set<Book> bookSet = new TreeSet<>(books.values());

            return bookSet;
        }
    }

    private Author extractAuthorFromResultSet(ResultSet rs) throws SQLException {
        Author author = new Author(rs.getString("a_first_name"),
                rs.getString("a_last_name"),
                rs.getString("a_middle_name"),
                rs.getInt("a_author_id"));
        author.setId(rs.getInt("a_id"));

        return author;
    }
}