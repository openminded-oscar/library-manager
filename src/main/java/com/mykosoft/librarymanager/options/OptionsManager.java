package com.mykosoft.librarymanager.options;

import com.mykosoft.librarymanager.ConsoleReader;
import com.mykosoft.librarymanager.options.processors.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by oleh on 01.01.17.
 */
@Component
@Log4j
public class OptionsManager {
    private static Map<Options, OptionProcessor> optionsProcessors;
    private static ConsoleReader reader = new ConsoleReader();

    @Autowired
    private ShowAllBooksProcessor showAllBooksProcessor;
    @Autowired
    private AddBookProcessor addBookProcessor;
    @Autowired
    private RemoveBookProcessor removeBookProcessor;
    @Autowired
    private EditBookProcessor editBookProcessor;
    @Autowired
    private ExitProcessor exitProcessor;


    @PostConstruct
    void initOptionsMap() {
        optionsProcessors = new EnumMap<>(Options.class);
        optionsProcessors.put(Options.ALL, showAllBooksProcessor);
        optionsProcessors.put(Options.ADD, addBookProcessor);
        optionsProcessors.put(Options.REMOVE, removeBookProcessor);
        optionsProcessors.put(Options.EDIT, editBookProcessor);
        optionsProcessors.put(Options.EXIT, exitProcessor);

        System.out.println(optionsProcessors);
    }

    public void manageOptions() {
        String userInput = null;
        while (true) {
            try {
                showMenu();

                userInput = reader.readLine();

                userInput = userInput.toUpperCase();
                Options optionSelected = Options.valueOf(userInput);
                if (optionSelected != null) {
                    OptionProcessor optionProcessor = optionsProcessors.get(optionSelected);
                    optionProcessor.executeCommand();
                    if (optionProcessor.isTerminating()) {
                        break;
                    }
                }
            } catch (IOException e) {
                log.info(e);
            }
        }

    }

    private void showMenu() {
        for (Options option : Options.values()) {
            System.out.println(option.getDescription());
        }
    }
}
