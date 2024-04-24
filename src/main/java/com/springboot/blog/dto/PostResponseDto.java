package com.springboot.blog.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostResponseDto {

    private long id;
    private String title;
    private String description;
    private String content;

    private Set<CommentResponseDto> comments;
    //Object in Response to the - > Client


}
