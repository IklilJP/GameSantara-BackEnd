package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.request.TagRequest;
import com.example.rakyatgamezomeapi.model.dto.response.TagResponse;
import com.example.rakyatgamezomeapi.model.entity.Tag;

import java.util.List;

public interface TagService {
    TagResponse createTag(TagRequest tagRequest);
    TagResponse updateTag(TagRequest tagRequest);
    TagResponse getTagById(String id);
    Tag getTagByIdForTrx(String id);
    List<TagResponse> getAllTags();
}