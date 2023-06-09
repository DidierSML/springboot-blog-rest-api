package com.springboot.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostResponseDto {

    private long id;

    @NotEmpty
    @Size(min = 2, message = "post title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotEmpty
    @Size(min = 2, message = "post title should have at least 2 characters")
    private String content;

    private Set<CommentResponseDto> comments; //Object in response to the Client


}
