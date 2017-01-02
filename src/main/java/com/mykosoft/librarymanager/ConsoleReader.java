package com.mykosoft.librarymanager;

import com.mykosoft.librarymanager.formatters.BookFormatter;
import com.mykosoft.librarymanager.model.Author;
import com.mykosoft.librarymanager.model.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleh on 01.01.17.
 */
public class ConsoleReader {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static BookFormatter bookFormatter = new BookFormatter();

    public String readLine() throws IOException {
        String input = reader.readLine();

        return input;
    }

    public String readLine(String prompt) throws IOException {
        System.out.println(prompt);
        return readLine();
    }

    public Integer readInteger() throws IOException {
        String input = reader.readLine();

        return Integer.valueOf(input);
    }

    public Integer readInteger(String prompt) throws IOException {
        System.out.println(prompt);

        return readInteger();
    }

    public Book readBookObject() throws IOException {
        String title = readLine("Input book title:");

        List<Author> authorList = new ArrayList<>();
        System.out.println("Input book authors:");
        for (int i = 0; ; ++i) {
            try {
                Author author = readAuthorObject();
                authorList.add(author);

            }catch (Exception e){
                System.out.println("Exception on author adding. Author not added.");
            }

            String oneMore = readLine("+ for one more author, sth else to stop input");
            if (!oneMore.equals("+")) {
                break;
            }
        }

        Book book = new Book(title, authorList);

        return book;
    }

    public Author readAuthorObject() throws IOException {
        String firstName = readLine("Input first name:").trim();
        String lastName = readLine("Input last name[required]:").trim();
        String middleName = readLine("Input middle name:").trim();
        Integer authorId = readInteger("Input author id number[required]");


        Author author = new Author(firstName, lastName, middleName, authorId);

        return author;
    }
}
