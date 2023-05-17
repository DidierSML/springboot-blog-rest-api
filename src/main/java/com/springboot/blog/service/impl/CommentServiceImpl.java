package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final ModelMapper mapper;


    @Override //Logic for (create/save) Comment
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        //retrieve post Entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        //set post to comment entity
        comment.setPost(post);

        //comment Entity to DB -invoking save method provided by JPA
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override //Logic for (getCommentsByPostId) All
    public List<CommentDto> getCommentsByPostId(long postId) {

        //retrieve comments by postId -invoking find method provided by JPA
        List<Comment> comments = commentRepository.findByPostId(postId);

        //convert list of comment Entities to List of comments Dto
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    //Logic to get a Comment By Id
    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        //retrieve (post) Entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        //retrieve (comment) Entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));

        //Condition of searching -if comment(id) does not belong to post(id) throw ...
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    //Logic to update a Comment
    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {

        //retrieve (post) Entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        //retrieve (comment) Entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));

        //Condition of searching -if comment(id) does not belong to post(id) throw ...
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        //Setting the new values for object Comment
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        //Saving new updated Comment Entity into DB -invoking save method provided by JPA
        Comment updatedComment = commentRepository.save(comment);

        //returning the Dto Comment Updated
        return mapToDto(updatedComment);

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



    private CommentDto mapToDto(Comment comment) { //mapper using ModelMapper -Entity To Dto
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) { //mapper using ModelMapper -Dto To Entity
        Comment comment = mapper.map(commentDto, Comment.class);

        return comment;
    }


/*
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
