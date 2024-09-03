package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.model.dto.request.TagRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.TagResponse;
import com.example.rakyatgamezomeapi.service.TagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIUrl.TAGS_API)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<CommonResponse<TagResponse>> createTag(@Valid @RequestBody TagRequest tagRequest) {
        TagResponse tagResponse = tagService.createTag(tagRequest);
        CommonResponse<TagResponse> commonResponse = CommonResponse.<TagResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tag created successfully")
                .data(tagResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping()
    public ResponseEntity<CommonResponse<TagResponse>> updateTag(@Valid @RequestBody TagRequest tagRequest) {
        TagResponse tagResponse = tagService.updateTag(tagRequest);
        CommonResponse<TagResponse> commonResponse = CommonResponse.<TagResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Tag updated successfully")
                .data(tagResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TagResponse>> getTagById(@PathVariable String id) {
        TagResponse tagResponse = tagService.getTagById(id);
        CommonResponse<TagResponse> commonResponse = CommonResponse.<TagResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Tag get successfully")
                .data(tagResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TagResponse>>> getAllTags() {
        List<TagResponse> tagResponses = tagService.getAllTags();
        CommonResponse<List<TagResponse>> commonResponse = CommonResponse.<List<TagResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Tags get successfully")
                .data(tagResponses)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

}