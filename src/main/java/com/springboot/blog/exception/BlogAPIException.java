package com.springboot.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//We throw this exception whenever we write some business logic or validate request parameters
@Getter
@AllArgsConstructor
public class BlogAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;
}
