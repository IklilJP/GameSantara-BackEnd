package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.response.ProfilePictureResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProfilePictureService {
    ProfilePictureResponse upload( MultipartFile file, String userId, String url );
    byte[] download(String fileName);
}
