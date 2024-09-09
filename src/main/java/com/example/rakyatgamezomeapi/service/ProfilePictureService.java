package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.model.dto.response.ProfilePictureResponse;
import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import org.springframework.web.multipart.MultipartFile;

public interface ProfilePictureService {
    ProfilePicture upload(MultipartFile file, String userId );
    ProfilePicture createDefaultProfilePicture(String userId );
//    byte[] download(String fileName);
}
