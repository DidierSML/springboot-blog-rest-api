package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable; //Importing add from Instructor
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;


    @Override
    public PostDto createPost(PostDto postDto) {

        //convert - PostDto to Entity
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);


        //convert - Entity to PostDto
        PostDto postResponse = mapToDto(newPost);

        return postResponse;



    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) { //adding pageable

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() //Conditional to establish sorting, asc or desc
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort); //This method is provided by instructor and receive as parameter a String

        Page <Post> posts = postRepository.findAll(pageable);

        // get content from page object

        List <Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        //Method of Java 8 that convert -ListEntity(Post) in List(PostDto)

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow (()-> new ResourceNotFoundException ("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //first Browsing by Id
        Post post = postRepository.findById(id).orElseThrow (()-> new ResourceNotFoundException ("Post", "id", id));
        // fix values from the Entity using Dto
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        // save new values throught object updatePost using JPA.save
        Post updatePost = postRepository.save(post);
        //return object updated to the Client (Dto)
        return mapToDto(updatePost);
    }

    @Override
    public void deletePostById(long id) { //public void ...
        //first Browsing by Id
        Post post = postRepository.findById(id).orElseThrow (()-> new ResourceNotFoundException ("Post", "id", id));
        //we proceed to delete Id using Jpa Repository
        postRepository.delete(post);
    }

    //private method to Convert (Entity To Dto)
    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

    //private method to Convert (Dto to Entity)
    private Post mapToEntity (PostDto postDto){
        Post post = new Post();

        post.setId(post.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;

    }
}


     /* Anterior - Post(Entity) to PostDto-

        PostDto postResponse = new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());

        return postResponse;
    */
