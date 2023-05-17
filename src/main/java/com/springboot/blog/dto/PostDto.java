package com.springboot.blog.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDto {

    private long id;
    private String title;
    private String description;
    private String content;

    private Set<CommentDto> comments; //Se usará una lista para retornar el post con sus comentarios

}
