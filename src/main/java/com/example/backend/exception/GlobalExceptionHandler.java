package com.example.backend.exception;

import org.eclipse.angus.mail.iap.BadCommandException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LanguageNotSupportedException.class)
    public ResponseEntity<String> handleLanguageNotSupportedException(LanguageNotSupportedException exception){
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(exception.getMessage());
    }

    @ExceptionHandler(UserAccountException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAccountException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(IncompleteInformationException.class)
    public ResponseEntity<String> handleIncompleteInformationException(IncompleteInformationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<String> handleWeakPasswordException(WeakPasswordException exception){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(org.springframework.security.authentication.BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<String> handleInvalidDateException(InvalidDateException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(CodeSubmissionException.class)
    public ResponseEntity<String> handleCodeSubmissionException(CodeSubmissionException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
