package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.model.dto.request.PostCreateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.PostUpdateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.SearchPostRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.PagingResponse;
import com.example.rakyatgamezomeapi.model.dto.response.PostResponse;
import com.example.rakyatgamezomeapi.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIUrl.POST_API)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<PostResponse>>> getAllPosts(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "q", defaultValue = "") String query
    ) {
        SearchPostRequest request = SearchPostRequest.builder()
                .page(Math.max(page - 1, 0))
                .size(size)
                .query(query)
                .build();

        Page<PostResponse> posts = postService.getAllPosts(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(posts.getTotalPages())
                .totalElements(posts.getTotalElements())
                .page(page)
                .size(size)
                .hasPrevious(posts.hasPrevious())
                .hasNext(posts.hasNext())
                .build();

        CommonResponse<List<PostResponse>> commonResponse = CommonResponse.<List<PostResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("All Posts Data Retrieved Successfully")
                .paging(pagingResponse)
                .data(posts.getContent())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<PostResponse>> getPostById(@PathVariable("id") String id) {
        PostResponse post = postService.getPostById(id);
        CommonResponse<PostResponse> commonResponse = CommonResponse.<PostResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Post Data Retrieved Successfully")
                .data(post)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<PostResponse>> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest) {
        PostResponse post = postService.createPost(postCreateRequest);
        CommonResponse<PostResponse> commonResponse = CommonResponse.<PostResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Post Created Successfully")
                .data(post)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<PostResponse>> updatePost(@RequestBody PostUpdateRequest postUpdateRequest) {
        PostResponse post = postService.updatePost(postUpdateRequest);
        CommonResponse<PostResponse> commonResponse = CommonResponse.<PostResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Post Updated Successfully")
                .data(post)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deletePost(@PathVariable("id") String id) {
        postService.deletePost(id);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Post Deleted Successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
