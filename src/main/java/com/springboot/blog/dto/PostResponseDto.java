package com.springboot.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostResponseDto {

    private long id;


    private String title;


    private String description;


    private String content;

    private Set<CommentResponseDto> comments;

}
