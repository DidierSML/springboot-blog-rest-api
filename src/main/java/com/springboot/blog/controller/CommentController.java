package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentRequestDto;
import com.springboot.blog.dto.CommentResponseDto;
import com.springboot.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    //create/save (Comment in Post) rest api
    @PostMapping("saveCommentInPost/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment (@PathVariable (value = "postId") long postId,
                                             @Valid @RequestBody CommentRequestDto commentRequestDto){
        return commentService.createComment(postId, commentRequestDto);
    }

    //(get All comments for a particular Post)
    @GetMapping("getAllCommentsBy/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getCommentsByPostId (@PathVariable (value ="postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }

    //get comment by post id (postId, commentId)
    @GetMapping("postId/{postId}/commentId/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto getCommentById (@PathVariable (value = "postId") Long postId, @PathVariable (value = "commentId") Long commentId){
        return commentService.getCommentById(postId,commentId);
    }

    //updateComment (postId, commentId, commentDto)
    @PutMapping("postId/{postId}/commentId/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updateComment (@PathVariable(value = "postId") long postId,
                                             @PathVariable (value = "commentId") long commentId,
                                             @Valid @RequestBody CommentRequestDto commentRequestDto){

        return commentService.updateComment(postId,commentId,commentRequestDto);
    }

    //deleteComment (postId, commentId)
    @DeleteMapping("postId/{postId}/commentId/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment (@PathVariable(value = "postId") long postId,
                               @PathVariable (value = "commentId") long commentId){

         commentService.deleteCommentById(postId,commentId);
    }
}
