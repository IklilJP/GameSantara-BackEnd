package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.model.dto.request.TagRequest;
import com.example.rakyatgamezomeapi.model.dto.response.TagResponse;
import com.example.rakyatgamezomeapi.model.entity.Tag;
import com.example.rakyatgamezomeapi.repository.TagRepository;
import com.example.rakyatgamezomeapi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public TagResponse createTag(TagRequest tagRequest) {
        Tag tag = Tag.builder()
                .name(tagRequest.getName().trim())
                .imgUrl(tagRequest.getImgurl())
                .createdAt(Instant.now().toEpochMilli())
                .build();
        tag = tagRepository.save(tag);
        return mapToResponse(tag);
    }

    @Override
    public TagResponse updateTag(String id, TagRequest tagRequest) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        tag.setName(tagRequest.getName().trim());
        tag.setImgUrl(tagRequest.getImgurl());
        tag.setUpdatedAt(Instant.now().toEpochMilli());
        tag = tagRepository.save(tag);
        return mapToResponse(tag);
    }

    @Override
    public TagResponse getTagById(String id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        return mapToResponse(tag);
    }

    @Override
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private TagResponse mapToResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .imgUrl(tag.getImgUrl())
                .createdAt(tag.getCreatedAt())
                .updatedAt(tag.getUpdatedAt())
                .build();
    }
}