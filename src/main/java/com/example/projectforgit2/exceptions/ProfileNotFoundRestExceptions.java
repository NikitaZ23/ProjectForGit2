package com.example.projectforgit2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProfileNotFoundRestExceptions extends ResponseStatusException {
    public ProfileNotFoundRestExceptions(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
