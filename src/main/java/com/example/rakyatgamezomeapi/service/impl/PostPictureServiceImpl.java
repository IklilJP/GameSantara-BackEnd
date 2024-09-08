package com.example.rakyatgamezomeapi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.rakyatgamezomeapi.model.dto.response.PostPictureResponse;
import com.example.rakyatgamezomeapi.model.entity.Post;
import com.example.rakyatgamezomeapi.model.entity.PostPicture;
import com.example.rakyatgamezomeapi.repository.PostPictureRepository;
import com.example.rakyatgamezomeapi.service.PostPictureService;
import com.example.rakyatgamezomeapi.utils.exceptions.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostPictureServiceImpl implements PostPictureService {
    private final PostPictureRepository postPictureRepository;
    private final Cloudinary cloudinary;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostPicture uploadPicture(MultipartFile file, String postId) {
        String originalFileName = postId + "_" + file.getOriginalFilename();
        String sendFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        try{
            Map param = ObjectUtils.asMap(
                    "public_id", "post-pictures/"+sendFileName,
                    "use_filename", true,
                    "unique_filename", true,
                    "overwrite", false,
                    "quality", "auto:low",
                    "folder", "posts/"+postId+"/"
            );
            Map result = cloudinary.uploader().upload(file.getBytes(), param);
            final String url = (String) result.get("secure_url");
            PostPicture newPostPicture = PostPicture.builder()
                    .imageUrl(url)
                    .createdAt(System.currentTimeMillis())
                    .build();
            return postPictureRepository.save(newPostPicture);
        }catch (Exception e) {
            throw new FileStorageException("Failed to upload picture: " + e.getMessage());
        }
    }
}
