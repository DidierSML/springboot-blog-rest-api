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

        //Conversion from comments to List of CommentsToResponseDto and Response of this last
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


/*
    private CommentRequestDto mapToDto(Comment comment) { //mapper using ModelMapper -Entity To Dto
        CommentRequestDto commentDto = mapper.map(comment, CommentRequestDto.class);

        return commentDto;
    }

    private Comment mapToEntity(CommentRequestDto commentDto) { //mapper using ModelMapper -Dto To Entity
        Comment comment = mapper.map(commentDto, Comment.class);

        return comment;
    }



    //private method to Convert (Entity To Dto) --------------------------> In real scenarios is most util use Libraries like (ModelMapper or MapStruct)
        CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    //private method to Convert (Dto to Entity) --------------------------> In real scenarios is most util use Libraries like (ModelMapper or MapStruct)
        private Comment mapToEntity (CommentDto commentDto){
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;

    }

 */
}
