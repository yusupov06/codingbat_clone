package uz.md.leetcode.exceptions;

import org.springframework.http.HttpStatus;


public class GenericNotFoundException extends RestException {
    public GenericNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
