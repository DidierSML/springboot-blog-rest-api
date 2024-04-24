package com.springboot.blog.service;

import com.springboot.blog.dto.PostRequestDto;
import com.springboot.blog.dto.PostGeneralResponse;
import com.springboot.blog.dto.PostResponseDto;

public interface PostService {

    PostResponseDto createPost(PostRequestDto postRequestDto);
    PostGeneralResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostResponseDto getPostById (long id);

    PostResponseDto updatePost(PostRequestDto postDto, long id);

    void deletePostById (long id);
    //String deletePostById(long id);
}
