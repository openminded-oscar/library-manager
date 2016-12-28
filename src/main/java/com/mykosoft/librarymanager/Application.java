package com.mykosoft.librarymanager;

import com.mykosoft.librarymanager.service.BookCrudManager;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by oleh on 28.12.16.
 */
@SpringBootApplication
@Log4j
public class Application implements CommandLineRunner {
    @Autowired
    private BookCrudManager bookCrudManager;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings)  {
        bookCrudManager.findAllBooks();
    }
}
