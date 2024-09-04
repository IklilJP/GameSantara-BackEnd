package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.model.dto.request.TagRequest;
import com.example.rakyatgamezomeapi.model.dto.response.TagResponse;
import com.example.rakyatgamezomeapi.model.entity.Tag;
import com.example.rakyatgamezomeapi.repository.TagRepository;
import com.example.rakyatgamezomeapi.service.TagService;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import com.example.rakyatgamezomeapi.utils.exceptions.TagAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public TagResponse createTag(TagRequest tagRequest) {
        Tag tag = Tag.builder()
                .name(tagRequest.getName().trim())
                .imgUrl(tagRequest.getImgurl())
                .createdAt(Instant.now().toEpochMilli())
                .build();
        try{
            tag = tagRepository.saveAndFlush(tag);
        }catch(DataIntegrityViolationException e){
            throw new TagAlreadyExistException("Tag already exists");
        }
        return mapToResponse(tag);
    }

    @Override
    public TagResponse updateTag(TagRequest tagRequest) {
        Tag tag = tagRepository.findById(tagRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        tag.setName(tagRequest.getName().trim());
        tag.setImgUrl(tagRequest.getImgurl());
        tag.setUpdatedAt(Instant.now().toEpochMilli());
        try {
            tag = tagRepository.saveAndFlush(tag);
        }catch (DataIntegrityViolationException e){
            throw new TagAlreadyExistException("Tag already exists");
        }
        return mapToResponse(tag);
    }

    @Override
    public TagResponse getTagById(String id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        return mapToResponse(tag);
    }

    @Override
    public Tag getTagByIdForTrx(String id) {
        return tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
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