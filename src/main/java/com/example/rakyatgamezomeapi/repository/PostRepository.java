package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, String> {
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.tag " +
            " WHERE p.title LIKE %:query% OR p.body LIKE %:query% OR p.tag.name LIKE %:query%")
    Page<Post> findAllByTitleContainingOrBodyContaining(@Param("query") String query, Pageable pageable);
}
