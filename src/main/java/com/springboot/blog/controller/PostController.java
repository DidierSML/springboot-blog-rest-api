package com.springboot.blog.controller;


import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService; //Estamos usando la interfaz de Service

    //create blog post rest api
    @PostMapping("save")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto createPost (@RequestBody PostDto postDto){
        return postService.createPost(postDto);
    }

    //get all posts rest api
    @GetMapping("getAllPosts")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getAllPosts (
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
    public PostDto findById (@PathVariable (value = "id") Long id){
        return postService.getPostById(id);
    }

    //update Post api
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto updatePost (@RequestBody PostDto postDto, @PathVariable (name ="id") long id){
        PostDto postResponse = postService.updatePost(postDto,id);
        return postResponse;
    }

    //delete Post api
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