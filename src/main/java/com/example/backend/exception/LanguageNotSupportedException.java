package com.example.backend.exception;

import com.example.backend.model.Language;

public class LanguageNotSupportedException extends RuntimeException {

    public LanguageNotSupportedException(Language language) {
        super("The following language is not supported: " + language.toString());
    }

}