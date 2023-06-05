package com.springboot.blog.dto;

import lombok.Data;

@Data
public class CommentResponseDto {

    private long id;
    private String name;
    private String email;
    private String body;
}
