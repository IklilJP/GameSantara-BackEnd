package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.dto.request.PostCreateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.PostUpdateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.SearchPostRequest;
import com.example.rakyatgamezomeapi.model.dto.response.PostPictureResponse;
import com.example.rakyatgamezomeapi.model.dto.response.PostResponse;
import com.example.rakyatgamezomeapi.model.entity.Post;
import com.example.rakyatgamezomeapi.model.entity.PostPicture;
import com.example.rakyatgamezomeapi.model.entity.Tag;
import com.example.rakyatgamezomeapi.model.entity.User;
import com.example.rakyatgamezomeapi.repository.PostRepository;
import com.example.rakyatgamezomeapi.service.PostPictureService;
import com.example.rakyatgamezomeapi.service.PostService;
import com.example.rakyatgamezomeapi.service.TagService;
import com.example.rakyatgamezomeapi.service.UserService;
import com.example.rakyatgamezomeapi.utils.FileUploadUtil;
import com.example.rakyatgamezomeapi.utils.exceptions.AuthenticationException;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final TagService tagService;
    private final PostRepository postRepository;
    private final PostPictureService postPictureService;
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
    public PostResponse uploadPictures(List<MultipartFile> files, String postId) {
        Post post = findPostByIdOrThrow(postId);
        List<PostPicture> foundPictures = post.getPictures();
        files.forEach(file -> {
            FileUploadUtil.assertAllowedExtension(file, FileUploadUtil.IMAGE_PATTERN);
            PostPicture picture = postPictureService.uploadPicture(file, postId);
            foundPictures.add(picture);
            post.setPictures(foundPictures);
            post.setUpdatedAt(System.currentTimeMillis());
        });
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
                .pictures(post.getPictures()!= null ? post.getPictures().stream().map(PostPictureResponse::of).toList() : null)
                .tagName(post.getTag() != null ? post.getTag().getName() : "No Tag")
                .tagImgUrl(post.getTag() != null ? post.getTag().getImgUrl() : "No Image Tag")
                .commentsCount(post.getComments() != null ? (long) post.getComments().size(): 0)
                .upVotesCount(post.getVotes() != null ? post.getVotes().stream().filter(votePost -> votePost.getVoteType() == EVoteType.UPVOTE).count() : 0)
                .downVotesCount(post.getVotes() != null ? post.getVotes().stream().filter(votePost -> votePost.getVoteType() == EVoteType.DOWNVOTE).count() : 0)
                .createAt(post.getCreatedAt())
                .updateAt(post.getUpdatedAt())
                .build();
    }

    private Post findPostByIdOrThrow(String id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }
}
