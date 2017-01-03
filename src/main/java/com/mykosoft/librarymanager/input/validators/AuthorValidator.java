package com.mykosoft.librarymanager.input.validators;

import com.mykosoft.librarymanager.model.Author;
import org.springframework.stereotype.Component;

import static org.valid4j.Validation.validate;

/**
 * Created by oleh on 03.01.17.
 */
@Component
public class AuthorValidator implements InputValidator<Author> {
    public static final int MAX_FNAME_LENGTH = 64;
    public static final int MAX_LNAME_LENGTH = 64;
    public static final int MAX_MNAME_LENGTH = 128;

    public static final String FNAME_REGEX = "[A-Z]([ A-Z]){1," + MAX_FNAME_LENGTH + "}";
    public static final String LNAME_REGEX = "[A-Z]([ A-Z]){1," + MAX_LNAME_LENGTH + "}";
    public static final String MNAME_REGEX = "[A-Z]([ A-Z]){1," + MAX_MNAME_LENGTH + "}";

    @Override
    public void validateValue(Author author) {
        String fname = author.getFirstName();
        String lname = author.getLastName();
        String mname = author.getMiddleName();

        validate(lname.matches(LNAME_REGEX), new IllegalArgumentException("Not suitable last name"));

        if(fname!=null){
            validate(fname.matches(FNAME_REGEX), new IllegalArgumentException("Not suitable first name"));
        }

        if(mname!=null){
            validate(mname.matches(MNAME_REGEX), new IllegalArgumentException("Not suitable middle name"));
        }
    }
}
