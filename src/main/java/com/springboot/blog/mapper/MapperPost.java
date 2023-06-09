package com.springboot.blog.mapper;

import com.springboot.blog.dto.PostRequestDto;
import com.springboot.blog.dto.PostResponseDto;
import com.springboot.blog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperPost {

    MapperPost MAPPER_POST = Mappers.getMapper(MapperPost.class);

    Post requestDtoToPost (PostRequestDto postRequestDto);

    PostResponseDto postToResponseDto (Post post);

    List<PostResponseDto> postsToResponseDto (List<Post> postList);

}
