package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostRequestDto;
import com.springboot.blog.dto.PostGeneralResponse;
import com.springboot.blog.dto.PostResponseDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.mapper.MapperPost;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable; //Importing add from Instructor
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;

    private final MapperPost mapperPost; //using mapstruct


    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto) {

        //convert - PostDto to Entity
        Post post = mapperPost.requestDtoToPost(postRequestDto);

        Post newPost = postRepository.save(post);

        //convert - Entity to PostDto
        return mapperPost.postToResponseDto(newPost);

    }

    @Override
    public PostGeneralResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) { //adding pageable

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() //Conditional to establish sorting, asc or desc
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort); //This method is provided by instructor and receive as parameter a String

        Page <Post> posts = postRepository.findAll(pageable);

        // get content from page object
        //List <Post> listOfPosts = posts.getContent();

        //List<PostRequestDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        List<PostResponseDto> content = mapperPost.postsToResponseDto(posts.getContent());
        //Method of Java 8 that convert -ListEntity(Post) in List(PostDto)

        PostGeneralResponse postGeneralResponse = new PostGeneralResponse();

        postGeneralResponse.setContent(content);
        postGeneralResponse.setPageNo(posts.getNumber());
        postGeneralResponse.setPageSize(posts.getSize());
        postGeneralResponse.setTotalElements(posts.getTotalElements());
        postGeneralResponse.setTotalPages(posts.getTotalPages());
        postGeneralResponse.setLast(posts.isLast());

        return postGeneralResponse;

    }

    @Override
    public PostResponseDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow (()-> new ResourceNotFoundException ("Post", "id", id));

        //Post by id -> postResponseDto as id and Return of the last
        return mapperPost.MAPPER_POST.postToResponseDto(post);
    }

    @Override
    public PostResponseDto updatePost(PostRequestDto postRequestDto, long id) {
        //first Browsing by id
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // Updating the fields of the existing (post) with the values of the (postRequestDto)
        existingPost.setTitle(postRequestDto.getTitle());
        existingPost.setDescription(postRequestDto.getDescription());
        existingPost.setContent(postRequestDto.getContent());

        //Saving new updated Comment Entity into DB -invoking save method provided by JPA
        existingPost = postRepository.save(existingPost);

        //Returning new updated Post as PostResponseDto
        return mapperPost.postToResponseDto(existingPost);
    }

    @Override
    public void deletePostById(long id) { //public void ...
        //first Browsing by id
        Post post = postRepository.findById(id).orElseThrow (()-> new ResourceNotFoundException ("Post", "id", id));
        //we proceed to delete id using Jpa Repository
        postRepository.delete(post);
    }

}

 /*
        //private method to Convert (Entity To Dto) ---Using Model Mapper
        private PostRequestDto mapToDto(Post post){
            PostRequestDto postDto = mapper.map(post, PostRequestDto.class);
            PostDto postDto = new PostDto();
            postDto.setId(post.getId());
            postDto.setTitle(post.getTitle());
            postDto.setDescription(post.getDescription());
            postDto.setContent(post.getContent());
            return postDto;
        }
        //private method to Convert (Dto to Entity) ---Using ModelMapper
        private Post mapToEntity (PostRequestDto postDto){
            Post post = mapper.map(postDto, Post.class);
            Post post = new Post();
            post.setId(post.getId());
            post.setTitle(postDto.getTitle());
            post.setDescription(postDto.getDescription());
            post.setContent(postDto.getContent());
            return post;
        }
        Anterior - Post(Entity) to PostDto- (With Out - ModelMapper)
        PostDto postResponse = new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());
        return postResponse;
    */

