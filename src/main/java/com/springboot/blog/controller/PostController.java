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

    //Create/Save a Post in Blog
    @PostMapping("save")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost (@Valid @RequestBody PostRequestDto postRequestDto){

        return postService.createPost(postRequestDto);
    }

    //Get all posts (PostGeneralResponse)
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

    //Get post By ID
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto findById (@PathVariable (value = "id") Long id){

        return postService.getPostById(id);
    }

    //Update Post By ID
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto updatePost (@Valid @RequestBody PostRequestDto postRequestDto,
                                       @PathVariable (name ="id") long id){

        return postService.updatePost(postRequestDto,id);
    }

    //Delete Post By ID
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost (@PathVariable (name ="id") long id){

        postService.deletePostById(id);
    }

}


/*

  1     //create blog post rest api

        @PostMapping("save")
        public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
            return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
        }


  2     //Get All posts api - using ResponseEntity

       @GetMapping("getAllPosts")
            public List<PostDto> getAllPosts(){
                return postService.getAllPosts();
        }

  3     //Get Post By Id api - using ResponseEntity

       @GetMapping("/{id}")
       public ResponseEntity <PostDto> getPostById (@PathVariable (name = "id") long id) {
            return ResponseEntity.ok(postService.getPostById(id));
       }

  4      //Update Post api - using ResponseEntity

        @PutMapping("{id}")
        public ResponseEntity <PostDto> updatePost (@RequestBody PostDto postDto, @PathVariable (name ="id"), long id){
            PostDto postResponse = postService.updatePost(postDto, id);
            return new ResponseEntity<>(postResponse, HttpStatus.OK);
        }

  5      //delete Post api - using ResponseEntity

        @DeleteMapping("{id})
        public ResponseEntity <String> deletePost(@PathVariable (name = "id") long id){
            postService.deletePostById(id);
            return new ResponseEntity <> ("Post entity deleted successfully.", HttpStatus.Ok);
        }

     */