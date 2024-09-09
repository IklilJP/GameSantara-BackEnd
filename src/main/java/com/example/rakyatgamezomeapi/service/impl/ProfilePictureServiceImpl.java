package com.example.rakyatgamezomeapi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.rakyatgamezomeapi.model.dto.response.ProfilePictureResponse;
import com.example.rakyatgamezomeapi.model.entity.ProfilePicture;
import com.example.rakyatgamezomeapi.repository.ProfilePictureRepository;
import com.example.rakyatgamezomeapi.service.ProfilePictureService;
import com.example.rakyatgamezomeapi.utils.exceptions.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfilePictureServiceImpl implements ProfilePictureService {
    private final ProfilePictureRepository profilePictureRepository;
    private final Cloudinary cloudinary;
//    private final Path rootLocation;

//    @Autowired
//    public ProfilePictureServiceImpl(ProfilePictureRepository profilePictureRepository) {
//        this.profilePictureRepository = profilePictureRepository;
//        this.rootLocation = Path.of("assets/images/profile-pictures");
//        try{
//            Files.createDirectories(rootLocation);
//        }catch (IOException e){
//            throw new FileStorageException("Failed to initialize file storage service");
//        }
//    }

    @Override
    public ProfilePicture upload(MultipartFile file, String userId) {
        try{
            Map param1 = ObjectUtils.asMap(
                    "public_id", "user-profile-pictures/"+userId,
                    "use_filename", true,
                    "unique_filename", true,
                    "overwrite", true,
                    "quality", "auto:low",
                    "folder", "profile-pictures"
            );
            Map result = cloudinary.uploader().upload(file.getBytes(), param1);
            final String url = (String) result.get("secure_url");
            ProfilePicture foundProfilePicture = profilePictureRepository.findByUserId(userId).orElse(null);
            if(foundProfilePicture != null) {
                foundProfilePicture.setImage(url);
                foundProfilePicture.setUpdatedAt(System.currentTimeMillis());
            }else {
                foundProfilePicture = ProfilePicture.builder()
                        .image(url)
                        .createdAt(System.currentTimeMillis())
                        .build();
            }
            return profilePictureRepository.saveAndFlush(foundProfilePicture);
        }catch (Exception e){
            throw new FileStorageException("Failed to upload profile picture: " + e.getMessage());
        }
    }

    @Override
    public ProfilePicture createDefaultProfilePicture(String userId) {
        ProfilePicture foundProfilePicture = profilePictureRepository.findByUserId(userId).orElse(null);
        if(foundProfilePicture != null) {
            foundProfilePicture.setImage("https://res.cloudinary.com/dpofjmzdu/image/upload/v1724926159/assets/pp-notfound.jpg");
            foundProfilePicture.setUpdatedAt(System.currentTimeMillis());
        }else{
            foundProfilePicture = ProfilePicture.builder()
                    .image("https://res.cloudinary.com/dpofjmzdu/image/upload/v1724926159/assets/pp-notfound.jpg")
                    .createdAt(System.currentTimeMillis())
                    .build();
        }
        return profilePictureRepository.saveAndFlush(foundProfilePicture);
    }

//    @Override
//    public byte[] download(String fileName) {
//        String filename = null;
//        try {
//            filename = "assets/images/profile-pictures/" + fileName;
//            Path path = Paths.get(filename);
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            Thumbnails.of(path.toFile())
//                    .size(200, 200)
//                    .outputFormat("png")
//                    .toOutputStream(outputStream);
//
//            return outputStream.toByteArray();
//        } catch (IOException e) {
//            throw new FileStorageException("Could not load file: " + filename + ": " + e);
//        }
//    }

    private ProfilePictureResponse toResponse(ProfilePicture profilePicture) {
        return ProfilePictureResponse.builder()
                .imageUrl(profilePicture.getImage())
                .build();
    }
}
