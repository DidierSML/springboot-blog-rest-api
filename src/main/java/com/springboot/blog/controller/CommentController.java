package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    //create comment with post rest api
    @PostMapping("/save/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment (@PathVariable (value = "postId") long postId, @RequestBody CommentDto commentDto){
        return commentService.createComment(postId, commentDto);
    }

}

    /*
     //create blog post rest api
     @PostMapping("/save/{postId}/comments")
     public ResponseEntity<CommentDto> createComment (@PathVariable (value = "postId") long postId,
                                                      @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto),HttpStatus.CREATED;
    }

     */
