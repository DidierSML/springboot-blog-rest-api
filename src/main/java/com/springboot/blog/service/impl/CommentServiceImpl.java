package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentRequestDto;
import com.springboot.blog.dto.CommentResponseDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.mapper.MapperComment;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final MapperComment mapperComment;// mapstruct


    @Override //Logic for (create/save) Comment
    public CommentResponseDto createComment(long postId, CommentRequestDto commentRequestDto) {

        //-Converting CommentRequestDto in Comment using Mapstruct
        Comment comment = mapperComment.requestDtoToComment(commentRequestDto);

        //retrieve post Entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        //set post to comment entity
        comment.setPost(post);

        //Saving "new Comment" Entity using save method provided by JPA
        Comment newComment = commentRepository.save(comment);

        //Returning to the Client the commentResponseDto
        return mapperComment.commentToResponseDto(newComment);
    }

    @Override //Logic for (getCommentsByPostId) All
    public List<CommentResponseDto> getCommentsByPostId(long postId) {

        //retrieve comments by postId -invoking find method provided by JPA
        List<Comment> comments = commentRepository.findByPostId(postId);

        //Conversion from List of Comments in CommentsToResponseDto and Response of (CommentsToResponseDto)
        return mapperComment.MAPPER_COMMENT.commentsToResponseDto(comments);

    }

    //Logic to get a Comment By Id
    @Override
    public CommentResponseDto getCommentById(Long postId, Long commentId) {

        //retrieve (post) Entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        //retrieve (comment) Entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));

        //Condition of searching -if comment(id) does not belong to post(id) throw ...
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        //Returning to the Client the commentResponseDto by id
        return mapperComment.commentToResponseDto(comment);
    }

    //Logic to update a Comment
    @Override
    public CommentResponseDto updateComment(long postId, long commentId, CommentRequestDto commentRequestDto) {

        //retrieve (post) Entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        //retrieve (comment) Entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));

        //Condition of searching -if comment(id) does not belong to post(id) throw ...
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentRequestDto.getName());
        comment.setEmail(commentRequestDto.getEmail());
        comment.setBody(comment.getBody());
        comment.setPost(post); //asignando la publicación al comentario

        //-Converting CommentRequestDto in Comment using Mapstruct
        //comment = mapperComment.requestDtoToComment(commentRequestDto);

        //Saving new updated Comment Entity into DB -invoking save method provided by JPA
        Comment updatedComment = commentRepository.save(comment);

        //Returning updatedComment as CommentResponseDto
        return mapperComment.commentToResponseDto(updatedComment);

    }

    //Logic to delete a Comment By Id
    @Override
    public void deleteCommentById(Long postId, Long commentId) {

        //retrieve (post) Entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        //retrieve (comment) Entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));

        //Condition of searching -if comment(id) does not belong to post(id) throw ...
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        //Invoking the delete method provided by JPA
        commentRepository.delete(comment);
    }

}
