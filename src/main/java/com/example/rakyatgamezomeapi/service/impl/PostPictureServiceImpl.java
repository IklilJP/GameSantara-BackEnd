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
        try{
            Map param = ObjectUtils.asMap(
                    "public_id", "post-pictures/"+postId+"_"+file.getName(),
                    "use_filename", true,
                    "unique_filename", true,
                    "overwrite", true,
                    "folder", "posts/"+postId+"/"
            );
            Map result = cloudinary.uploader().upload(file.getBytes(), param);
            final String url = (String) result.get("secure_url");
            List<PostPicture> postPictures = postPictureRepository.findAllByPostId(postId);
            PostPicture newPostPicture = new PostPicture();
            if(!postPictures.isEmpty()) {
                newPostPicture.setImageUrl(url);
                newPostPicture.setUpdatedAt(System.currentTimeMillis());
                postPictures.add(newPostPicture);
            }else{
                newPostPicture.setImageUrl(url);
                newPostPicture.setCreatedAt(System.currentTimeMillis());
                postPictures.add(newPostPicture);
            }
            return postPictureRepository.save(newPostPicture);
        }catch (Exception e) {
            throw new FileStorageException("Failed to upload picture: " + e.getMessage());
        }
    }
}
