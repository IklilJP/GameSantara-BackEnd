package com.example.rakyatgamezomeapi.service;


import com.example.rakyatgamezomeapi.model.dto.request.PostCreateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.PostUpdateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.SearchPostRequest;
import com.example.rakyatgamezomeapi.model.dto.response.PostResponse;
import com.example.rakyatgamezomeapi.model.entity.Post;
import org.springframework.data.domain.Page;

public interface PostService {
    Page<PostResponse> getAllPosts(SearchPostRequest request);
    PostResponse getPostById(String id);
    Post getPostByIdForTrx(String id);
    PostResponse createPost(PostCreateRequest request);
    PostResponse updatePost(PostUpdateRequest request);
    void deletePost(String id);
}
