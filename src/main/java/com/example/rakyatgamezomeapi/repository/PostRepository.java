package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, String> {
    @Query("SELECT p FROM Post p LEFT JOIN p.tag t " +
            "WHERE p.title LIKE %:query% OR CAST(p.body AS STRING) LIKE %:query% OR t.name LIKE %:query%")
    Page<Post> findAllByTitleContainingOrBodyContaining(@Param("query") String query, Pageable pageable);
}
