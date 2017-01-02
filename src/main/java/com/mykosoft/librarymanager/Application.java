package com.mykosoft.librarymanager;

import com.mykosoft.librarymanager.options.OptionsManager;
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
    private OptionsManager optionsManager;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) {
        System.out.println("Hi! Welcome to library manager!");
        optionsManager.manageOptions();




        //        List<Author> authors = new ArrayList<Author>();
//        authors.add(new Author("Petrenko", "Petro", null, 2344));
//        authors.add(new Author("Ivanenko", "Ivan", null, 1443));
//
//
//        bookCrudManager.findAllBooks();
//        bookCrudManager.addBook(new Book("Math 9 form", authors));
    }
}
