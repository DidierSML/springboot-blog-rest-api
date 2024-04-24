package com.springboot.blog.controller;


import com.springboot.blog.dto.PostRequestDto;
import com.springboot.blog.dto.PostGeneralResponse;
import com.springboot.blog.dto.PostResponseDto;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    //create blog post rest api
    @PostMapping("save")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost (@Valid @RequestBody PostRequestDto postRequestDto){

        return postService.createPost(postRequestDto);
    }

    //get all posts rest api (PostGeneralResponse)
    @GetMapping("getAllPosts")
    @ResponseStatus(HttpStatus.OK)
    public PostGeneralResponse getAllPosts (
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,       //Its start from 0
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,   //Each Page will contain 10 posts, you can change it in postman
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,  //Its allows sorting information by default in asc
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir  //Its allows sorting information in asc or desc
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //get post by Id
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto findById (@PathVariable (value = "id") Long id){

        return postService.getPostById(id);
    }

    //update Post api
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto updatePost (@Valid @RequestBody PostRequestDto postRequestDto,
                                       @PathVariable (name ="id") long id){

        return postService.updatePost(postRequestDto,id);
    }

    //delete Post api
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost (@PathVariable (name ="id") long id){

        postService.deletePostById(id);
    }

}