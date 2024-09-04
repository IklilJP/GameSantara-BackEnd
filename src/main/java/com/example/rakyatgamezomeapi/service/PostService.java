package com.example.rakyatgamezomeapi.service;


import com.example.rakyatgamezomeapi.model.dto.request.PostCreateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.PostUpdateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.SearchPostRequest;
import com.example.rakyatgamezomeapi.model.dto.response.PostResponse;
import com.example.rakyatgamezomeapi.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    Page<PostResponse> getAllPosts(SearchPostRequest request);
    Page<PostResponse> getAllLatestPosts(SearchPostRequest request);
    Page<PostResponse> getAllTrendingPosts(SearchPostRequest request);
    Page<PostResponse> getAllUserContextPosts(SearchPostRequest request);
    PostResponse getPostById(String id);
    Post getPostByIdForTrx(String id);
    PostResponse createPost(PostCreateRequest request, List<MultipartFile> files);
    PostResponse updatePost(PostUpdateRequest request, List<MultipartFile> files);
    void deletePost(String id);
}
