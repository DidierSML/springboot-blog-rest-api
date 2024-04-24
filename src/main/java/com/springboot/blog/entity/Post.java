package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Getter //Note 1
@Setter //Note 1
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
      )
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="title", nullable = false)
    private String title;

    @Column(name ="description", nullable = false)
    private String description;

    @Column(name ="content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    //This 'post' establish a connection with the 'post' present in 'Comments' class
    private Set<Comment> comments = new HashSet<>();
    //Set does not allow duplicates for collections
}

/*
  Note 1: -Model Mapper- internally uses toString() method to print the result are for mapping, and that generate a loop
           of mappings post and postDto; in consequence, when you tried to consult the (posts methods), you cant see the
           (comments) for each one [(getAllPosts) , (getPostById)].
           The solution for it is change the @Data in the entity class (post) just for (@Getter & @Setter)

*/

