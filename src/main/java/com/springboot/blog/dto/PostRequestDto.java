package com.springboot.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostRequestDto {

    private long id;

    @NotEmpty
    @Size(min = 2, message ="Post title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message ="Post description should have at least 10 characters")
    private String description;

    @NotEmpty
    @Size(min = 2, message ="Post content should have at least 2 characters")
    private String content;

    //Probar
    private Set<CommentRequestDto> comments;

}
