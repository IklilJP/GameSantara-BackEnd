package com.example.rakyatgamezomeapi.repository;

import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, String> {
    @Query("SELECT p FROM Post p LEFT JOIN p.tag t " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(CAST(p.body AS STRING)) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(t.name) LIKE LOWER(CONCAT('%',:query,'%'))")
    Page<Post> findAllByTitleContainingOrBodyContaining(@Param("query") String query, Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN p.tag t " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(CAST(p.body AS STRING)) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(t.name) LIKE LOWER(CONCAT('%',:query,'%')) " +
            "ORDER BY (SIZE(p.votes) + SIZE(p.comments)) DESC")
    Page<Post> findAllAndSortByTrending(@Param("query") String query, Pageable pageable);

    Page<Post> findAllByVotesUserIdAndVotesVoteType(String userId, EVoteType voteType, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN p.votes v " +
            "LEFT JOIN p.comments c " +
            "GROUP BY p " +
            "ORDER BY (COUNT(v) + COUNT(c)) DESC")
    Page<Post> findAllOrderByVotesAndComments(Pageable pageable);

    Page<Post> findAllByTagId(String tagId, Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN p.tag t " +
            "WHERE p.tag.id = :tagId AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(CAST(p.body AS STRING)) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(t.name) LIKE LOWER(CONCAT('%',:query,'%')))")
    Page<Post> findAllByTagIdAndQueryContaining(@Param("tagId") String tagId, @Param("query") String query, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN p.votes v " +
            "LEFT JOIN p.comments c " +
            "WHERE p.tag.id = :tagId " +
            "GROUP BY p " +
            "ORDER BY (COUNT(v) + COUNT(c)) DESC")
    Page<Post> findAllByTagIdOrderByVotesAndComments(@Param("tagId") String tagId, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN p.votes v " +
            "LEFT JOIN p.comments c " +
            "LEFT JOIN p.tag t " +
            "WHERE p.tag.id = :tagId AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(CAST(p.body AS STRING)) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(t.name) LIKE LOWER(CONCAT('%',:query,'%'))) " +
            "GROUP BY p " +
            "ORDER BY (COUNT(v) + COUNT(c)) DESC")
    Page<Post> findAllByTagIdAndQueryContainingOrderByVotesAndComments(@Param("tagId") String tagId, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "ORDER BY p.createdAt DESC")
    Page<Post> findAllOrderByCreatedAtDesc(Pageable pageable);

    Optional<Post> findByIdAndVotesUserIdAndVotesVoteType(String id, String userId, EVoteType voteType);
    Page<Post> findAllByUserId(String userId, Pageable pageable);
}
