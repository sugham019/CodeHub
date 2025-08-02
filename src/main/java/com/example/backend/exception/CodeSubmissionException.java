package com.example.backend.exception;

public class CodeSubmissionException extends RuntimeException {

    public CodeSubmissionException(String message) {
        super("Code submission rejected:\t" + message);
    }

}
