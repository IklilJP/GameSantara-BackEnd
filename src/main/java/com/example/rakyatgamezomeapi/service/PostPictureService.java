package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.response.PostPictureResponse;
import com.example.rakyatgamezomeapi.model.entity.PostPicture;
import org.springframework.web.multipart.MultipartFile;

public interface PostPictureService {
    PostPicture uploadPicture(MultipartFile file, String postId);
}
