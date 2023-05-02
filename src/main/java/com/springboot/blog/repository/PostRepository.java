package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//la @Repository no es necesario ponerla, puesto que JpaRepository la contiene, aun asi por buena practica se pone
@Repository
public interface PostRepository extends JpaRepository <Post,Long> {
}
