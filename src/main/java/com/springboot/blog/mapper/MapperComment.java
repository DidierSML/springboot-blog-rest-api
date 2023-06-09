package com.springboot.blog.mapper;

import com.springboot.blog.dto.CommentRequestDto;
import com.springboot.blog.dto.CommentResponseDto;
import com.springboot.blog.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperComment {

    MapperComment MAPPER_COMMENT = Mappers.getMapper(MapperComment.class);

    Comment requestDtoToComment (CommentRequestDto commentRequestDto);

    CommentResponseDto commentToResponseDto (Comment comment);

    List<CommentResponseDto> commentsToResponseDto (List<Comment> commentList);
}
