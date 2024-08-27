package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.model.dto.request.TagRequest;
import com.example.rakyatgamezomeapi.model.dto.response.TagResponse;
import com.example.rakyatgamezomeapi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIUrl.TAGS_API)
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@RequestBody TagRequest tagRequest) {
        TagResponse tagResponse = tagService.createTag(tagRequest);
        return new ResponseEntity<>(tagResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTag(@PathVariable String id, @RequestBody TagRequest tagRequest) {
        TagResponse tagResponse = tagService.updateTag(id, tagRequest);
        return new ResponseEntity<>(tagResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable String id) {
        TagResponse tagResponse = tagService.getTagById(id);
        return new ResponseEntity<>(tagResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<TagResponse> tagResponses = tagService.getAllTags();
        return new ResponseEntity<>(tagResponses, HttpStatus.OK);
    }

}