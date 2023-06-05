package com.springboot.blog.service;

import com.springboot.blog.dto.CommentRequestDto;
import com.springboot.blog.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto createComment (long postId, CommentRequestDto commentDto);

    List<CommentResponseDto> getCommentsByPostId (long postId);

    CommentResponseDto getCommentById (Long postId, Long commentId);

    CommentResponseDto updateComment (long postId, long commentId, CommentRequestDto commentDto);

    void deleteCommentById (Long postId, Long commentId);

}
