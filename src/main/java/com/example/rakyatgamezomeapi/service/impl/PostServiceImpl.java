package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.constant.ERole;
import com.example.rakyatgamezomeapi.constant.EVoteType;
import com.example.rakyatgamezomeapi.model.dto.request.PostCreateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.PostUpdateRequest;
import com.example.rakyatgamezomeapi.model.dto.request.SearchPostRequest;
import com.example.rakyatgamezomeapi.model.dto.response.PostPictureResponse;
import com.example.rakyatgamezomeapi.model.dto.response.PostResponse;
import com.example.rakyatgamezomeapi.model.entity.*;
import com.example.rakyatgamezomeapi.repository.PostRepository;
import com.example.rakyatgamezomeapi.service.*;
import com.example.rakyatgamezomeapi.utils.exceptions.AuthenticationException;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        Page<Post> allPosts;

        if(request.getQuery().isEmpty()){
            allPosts = postRepository.findAll(pageable);
        }else{
            allPosts = postRepository.findAllByTitleContainingOrBodyContaining(request.getQuery(), pageable);
        }

        if(allPosts.isEmpty()) {
            throw new ResourceNotFoundException("All posts is empty");
        }
        return allPosts.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> getAllLatestPosts(SearchPostRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("createdAt").descending());
        Page<Post> allPosts;
        if(request.getQuery().isEmpty()){
            allPosts = postRepository.findAll(pageable);
        }else{
            allPosts = postRepository.findAllByTitleContainingOrBodyContaining(request.getQuery(), pageable);
        }

        if(allPosts.isEmpty()) {
            throw new ResourceNotFoundException("All posts is empty");
        }
        return allPosts.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> getAllTrendingPosts(SearchPostRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Post> allPosts;

        if(request.getQuery().isEmpty()){
            allPosts = postRepository.findAllOrderByVotesAndComments(pageable);
        }else{
            allPosts = postRepository.findAllAndSortByTrending(request.getQuery(), pageable);
        }

        if (allPosts.isEmpty()) {
            throw new ResourceNotFoundException("All posts is empty");
        }
        return allPosts.map(this::toResponse);
    }


    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> getAllUserContextPosts(SearchPostRequest request) {
        User user = userService.getUserByTokenForTsx();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Post> allPosts = postRepository.findAllByUserId(user != null ? user.getId(): "notfound", pageable);
        if(allPosts.isEmpty()) {
            throw new ResourceNotFoundException("All posts is empty");
        }
        return allPosts.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> getAllUserPosts(SearchPostRequest request, String userId) {
        User user = userService.getUserByIdForTsx(userId);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Post> allPosts = postRepository.findAllByUserId(user.getId(), pageable);
        if(allPosts.isEmpty()) {
            throw new ResourceNotFoundException("All posts is empty");
        }
        return allPosts.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> getAllPostsWhichUserIdUpVotes(SearchPostRequest request, String userId) {
        User user = userService.getUserByIdForTsx(userId);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Post> allPosts = postRepository.findAllByVotesUserIdAndVotesVoteType(user.getId(), EVoteType.UPVOTE, pageable);
        if(allPosts.isEmpty()) {
            throw new ResourceNotFoundException("All posts is empty");
        }
        return allPosts.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> getAllPostsByTagId(SearchPostRequest request, String tagId) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Post> allPosts;

        if(request.getQuery().isEmpty()){
            allPosts = postRepository.findAllByTagId(tagId, pageable);
        }else{
            allPosts = postRepository.findAllByTagIdAndQueryContaining(tagId, request.getQuery(), pageable);
        }

        if(allPosts.isEmpty()) {
            throw new ResourceNotFoundException("All posts is empty");
        }

        return allPosts.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> getAllLatestPostsByTagId(SearchPostRequest request, String tagId) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("createdAt").descending());
        Page<Post> allPosts;
        if(request.getQuery().isEmpty()){
            allPosts = postRepository.findAllByTagId(tagId, pageable);
        }else{
            allPosts = postRepository.findAllByTagIdAndQueryContaining(tagId, request.getQuery(), pageable);
        }

        if(allPosts.isEmpty()) {
            throw new ResourceNotFoundException("All posts is empty");
        }
        return allPosts.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostResponse> getAllTrendingPostsByTagId(SearchPostRequest request, String tagId) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Post> allPost;

        if(request.getQuery().isEmpty()){
            allPost = postRepository.findAllByTagIdOrderByVotesAndComments(tagId, pageable);
        }else {
            allPost = postRepository.findAllByTagIdAndQueryContaining(tagId, request.getQuery(), pageable);
        }

        if(allPost.isEmpty()) {
            throw new ResourceNotFoundException("All posts is empty");
        }

        return allPost.map(this::toResponse);
    }


    @Override
    public PostResponse getPostById(String id) {
        return toResponse(findPostByIdOrThrow(id));
    }

    @Override
    public Post getPostByIdForTrx(String id) {
        return findPostByIdOrThrow(id);
    }

    @Transactional(rollbackFor = ResourceNotFoundException.class)
    @Override
    public PostResponse createPost(PostCreateRequest request, List<MultipartFile> files) {
        User user = userService.getUserByTokenForTsx();
        Tag tag = tagService.getTagByIdForTrx(request.getTagId());
        List<PostPicture> postPictureList = new ArrayList<>();
        Post post = Post.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .pictures(postPictureList)
                .user(user)
                .tag(tag)
                .createdAt(System.currentTimeMillis())
                .build();

        post = postRepository.save(post);

        Post finalPost = post;
        if(files != null && !files.isEmpty()) {
            files.forEach(file -> {
                PostPicture postPicture = postPictureService.uploadPicture(file, finalPost.getId());
                postPicture.setPost(finalPost);
                postPictureList.add(postPicture);
            });
        }

        return toResponse(postRepository.saveAndFlush(finalPost));
    }

    @Transactional(rollbackFor = ResourceNotFoundException.class)
    @Override
    public PostResponse updatePost(PostUpdateRequest request, List<MultipartFile> files) {
        User user = userService.getUserByTokenForTsx();
        Post post = findPostByIdOrThrow(request.getId());
        Tag tag = tagService.getTagByIdForTrx(request.getTagId());
        List<PostPicture> postPictureList = post.getPictures() == null ? new ArrayList<>() : post.getPictures();

        if(!Objects.equals(user.getId(), post.getUser().getId()) || !(user.getRole().getName().equals(ERole.ADMIN))) {
            throw new AuthenticationException("You don't have permission to update this post");
        }

        if(files != null && !files.isEmpty()) {
            files.forEach(file -> {
                PostPicture postPicture = postPictureService.uploadPicture(file, post.getId());
                postPicture.setPost(post);
                postPictureList.add(postPicture);
            });
        }

        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        post.setTag(tag);
        post.setPictures(postPictureList);
        post.setUpdatedAt(System.currentTimeMillis());
        return toResponse(postRepository.saveAndFlush(post));
    }

    @Transactional(rollbackFor = ResourceNotFoundException.class)
    @Override
    public void deletePost(String id) {
        User user = userService.getUserByTokenForTsx();
        Post post = this.findPostByIdOrThrow(id);
        if(!(user.getId().equals(post.getUser().getId())) && !(user.getRole().getName().equals(ERole.ADMIN))) {
            throw new AuthenticationException("You don't have permission to delete this post");
        }
        postRepository.delete(post);
    }

    private boolean isUpVoted(String postId){
        User user = userService.getUserByTokenForTsx();
        return postRepository.findByIdAndVotesUserIdAndVotesVoteType(postId, user != null ? user.getId(): "not found", EVoteType.UPVOTE).orElse(null) != null;
    }

    private boolean isDownVoted(String postId){
        User user = userService.getUserByTokenForTsx();
        return postRepository.findByIdAndVotesUserIdAndVotesVoteType(postId, user != null ? user.getId(): "not found", EVoteType.DOWNVOTE).orElse(null) != null;
    }

    private PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .user(post.getUser().getUsername())
                .userId(post.getUser().getId())
                .profilePictureUrl(post.getUser().getProfilePicture() == null ? null : post.getUser().getProfilePicture().getImage())
                .title(post.getTitle())
                .body(post.getBody())
                .pictures(post.getPictures()!= null ? post.getPictures().stream().map(PostPictureResponse::of).toList() : null)
                .tagName(post.getTag() != null ? post.getTag().getName() : "No Tag")
                .tagImgUrl(post.getTag() != null ? post.getTag().getImgUrl() : "No Image Tag")
                .commentsCount(post.getComments() != null ? (long) post.getComments().size(): 0)
                .isUpVoted(isUpVoted(post.getId()))
                .isDownVoted(isDownVoted(post.getId()))
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
