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

    //Create/Save (Comment in Post)
    @PostMapping("saveCommentInPost/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment (@PathVariable (value = "postId") long postId,
                                             @Valid @RequestBody CommentRequestDto commentRequestDto){
        return commentService.createComment(postId, commentRequestDto);
    }

    //(get All comments By Post ID)
    @GetMapping("getAllCommentsBy/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getCommentsByPostId (@PathVariable (value ="postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }

    //Get comment by post id (postId, commentId)
    @GetMapping("postId/{postId}/commentId/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto getCommentById (@PathVariable (value = "postId") Long postId, @PathVariable (value = "commentId") Long commentId){
        return commentService.getCommentById(postId,commentId);
    }

    //UpdateComment (postId, commentId, commentDto)
    @PutMapping("postId/{postId}/commentId/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updateComment (@PathVariable(value = "postId") long postId,
                                             @PathVariable (value = "commentId") long commentId,
                                             @Valid @RequestBody CommentRequestDto commentRequestDto){

        return commentService.updateComment(postId,commentId,commentRequestDto);
    }

    //DeleteComment (postId, commentId)
    @DeleteMapping("postId/{postId}/commentId/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment (@PathVariable(value = "postId") long postId,
                               @PathVariable (value = "commentId") long commentId){

         commentService.deleteCommentById(postId,commentId);
    }
}

/*
     //create blog post rest api
     @PostMapping("/save/{postId}/comments")
     public ResponseEntity<CommentDto> createComment (@PathVariable (value = "postId") long postId,
                                                      @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto),HttpStatus.CREATED;
    }
    //getCommentsByPostId
    @GetMapping("getAll/commentsBy/{postId})
    public List <CommentDto> getCommentsByPostId (@PathVariable (value= "postId") long postId){
        return commentService.getCommentsByPostId(postId);
    //getCommentById
    @GetMapping("postId/{postId}/commentId/{commentId}")
    public ResponseEntity<CommentDto> getCommentById (@PathVariable(value = "postId") Long postId,
                                                     (@PathVariable(value = "commentId") Long commentId,
        CommentDto commentDto = commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    //updateComment
    @PutMapping("postId/{postId}/commentId/{commentId}")
    public ResponseEntity<CommentDto> updateComment (@PathVariable(value = "postId") long postId,
                                                    @PathVariable (value = "commentId") long commentId,
                                                    @RequestBody CommentDto commentDto){
       CommentDto updateComment= commentService.updateComment(postId,commentId,commentDto);
       return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }
    //deleteComment
    @DeleteMapping("postId/{postId}/commentId/{commentId}")
    public ResponseEntity <String> deleteComment (@PathVariable(value = "postId") long postId,
                                                  @PathVariable (value = "commentId") long commentId {
       commentService.updateComment(postId,commentId,commentDto);
       return new ResponseEntity<>(body:"Comment deleted successfully, HttpStatus.NO_CONTENT);
    }
     */
