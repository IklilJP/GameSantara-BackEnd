package com.example.rakyatgamezomeapi.service.impl;

import com.example.rakyatgamezomeapi.model.dto.response.ProfilePictureResponse;
import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import com.example.rakyatgamezomeapi.repository.ProfilePictureRepository;
import com.example.rakyatgamezomeapi.service.ProfilePictureService;
import com.example.rakyatgamezomeapi.utils.exceptions.FileStorageException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ProfilePictureServiceImpl implements ProfilePictureService {
    private final ProfilePictureRepository profilePictureRepository;
    private final Path rootLocation;

    @Autowired
    public ProfilePictureServiceImpl(ProfilePictureRepository profilePictureRepository) {
        this.profilePictureRepository = profilePictureRepository;
        this.rootLocation = Path.of("assets/images/profile-pictures");
        try{
            Files.createDirectories(rootLocation);
        }catch (IOException e){
            throw new FileStorageException("Failed to initialize file storage service");
        }
    }

    @Override
    public ProfilePictureResponse upload(MultipartFile file, String userId, String url) {
        String fileName = file.getOriginalFilename();
        fileName = userId + "-" + fileName;
        try{
            Path targetLocation = rootLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            ProfilePicture profilePicture = ProfilePicture.builder()
                    .image(url)
                    .createdAt(System.currentTimeMillis())
                    .build();
            return toResponse(profilePictureRepository.saveAndFlush(profilePicture));
        }catch (IOException e){
            throw new FileStorageException("Failed to initialize file storage service");
        }
    }

    @Override
    public byte[] download(String fileName) {
        String filename = null;
        try {
            filename = "assets/images/profile-pictures/" + fileName;
            Path path = Paths.get(filename);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(path.toFile())
                    .size(200, 200)
                    .outputFormat("png")
                    .toOutputStream(outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new FileStorageException("Could not load file: " + filename + ": " + e);
        }
    }

    private ProfilePictureResponse toResponse(ProfilePicture profilePicture) {
        return ProfilePictureResponse.builder()
                .imageUrl(profilePicture.getImage())
                .build();
    }
}
