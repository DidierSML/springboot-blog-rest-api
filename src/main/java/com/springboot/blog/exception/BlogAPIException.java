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

/*We used @AllArgumentsConstructor, the same form is possible generate
 Constructor just for -RunTimeException() & RunTimeException(message:String)

 Also, methods @Getter have been created using lombok establishing access to them.

 */