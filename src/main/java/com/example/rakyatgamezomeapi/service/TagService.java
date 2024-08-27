package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.request.TagRequest;
import com.example.rakyatgamezomeapi.model.dto.response.TagResponse;

import java.util.List;

public interface TagService {
    TagResponse createTag(TagRequest tagRequest);
    TagResponse updateTag(String id, TagRequest tagRequest);
    TagResponse getTagById(String id);
    List<TagResponse> getAllTags();
}