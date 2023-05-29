package com.springboot.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
@Getter
@AllArgsConstructor
public class ErrorDetails { //These elements will be used in order to send a response more simple respect to the errors

    private Date timestamp;
    private String message;
    private String details;
}
