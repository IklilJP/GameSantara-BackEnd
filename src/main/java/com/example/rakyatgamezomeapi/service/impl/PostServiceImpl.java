package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.model.dto.request.PostCreateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.PostUpdateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.SearchPostRequest;
import com.example.rakyatgamezomeapi.model.dto.response.PostResponse;
import com.example.rakyatgamezomeapi.model.entity.Post;
import com.example.rakyatgamezomeapi.model.entity.Tag;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.repository.PostRepository;
import com.example.rakyatgamezomeapi.service.PostService;
import com.example.rakyatgamezomeapi.service.TagService;
import com.example.rakyatgamezomeapi.service.UserService;
import com.example.rakyatgamezomeapi.utils.exceptions.AuthenticationException;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final TagService tagService;
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> getAllPosts(SearchPostRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Post> allPosts = postRepository.findAllByTitleContainingOrBodyContaining(request.getQuery(), pageable);
        return allPosts.map(this::toResponse);
    }

    @Override
    public PostResponse getPostById(String id) {
        return toResponse(findPostByIdOrThrow(id));
    }

    @Override
    public Post getPostByIdForTrx(String id) {
        return findPostByIdOrThrow(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostResponse createPost(PostCreateRequest request) {
        User user = userService.getUserByTokenForTsx();
        Tag tag = tagService.getTagByIdForTrx(request.getTagId());
        Post post = Post.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .user(user)
                .tag(tag)
                .createdAt(System.currentTimeMillis())
                .build();

        return toResponse(postRepository.saveAndFlush(post));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostResponse updatePost(PostUpdateRequest request) {
        User user = userService.getUserByTokenForTsx();
        Post post = findPostByIdOrThrow(request.getId());
        Tag tag = tagService.getTagByIdForTrx(request.getTagId());

        if(!Objects.equals(user.getId(), post.getUser().getId())) {
            throw new AuthenticationException("You don't have permission to update this post");
        }

        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        post.setTag(tag);
        post.setUpdatedAt(System.currentTimeMillis());
        return toResponse(postRepository.saveAndFlush(post));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePost(String id) {
        User user = userService.getUserByTokenForTsx();
        Post post = this.findPostByIdOrThrow(id);
        if(!Objects.equals(user.getId(), post.getUser().getId())) {
            throw new AuthenticationException("You don't have permission to update this post");
        }
        postRepository.delete(post);
    }

    private PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .user(post.getUser().getUsername())
                .title(post.getTitle())
                .body(post.getBody())
                .tagName(post.getTag() != null ? post.getTag().getName() : "No Tag")
                .tagImgUrl(post.getTag() != null ? post.getTag().getImgUrl() : "No Image Tag")
                .createAt(post.getCreatedAt())
                .updateAt(post.getUpdatedAt())
                .build();
    }

    private Post findPostByIdOrThrow(String id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }
}
