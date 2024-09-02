package com.example.rakyatgamezomeapi.utils;

import com.example.rakyatgamezomeapi.utils.exceptions.FileCloudStorageException;
import com.example.rakyatgamezomeapi.utils.exceptions.FileStorageException;
import lombok.experimental.UtilityClass;
import org.hibernate.AssertionFailure;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUploadUtil {
    public static final long MAX_FILE_SIZE = 2*1024*1024;

    public static final String IMAGE_PATTERN = "([^\\s]+(\\s[^\\s]+)*\\.(?i)(jpg|jpeg|png|gif|bmp))";

    public static final String FILE_NAME_FORMAT = "%s_%s_%s";

    public static boolean isAllowedExtension(final String fileName, final String pattern){
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

    public static void assertAllowedExtension(MultipartFile file, String pattern){
        if(file.getSize() > MAX_FILE_SIZE){
            throw new FileCloudStorageException("File is oversize");
        }
        if (!isAllowedExtension(file.getOriginalFilename(), pattern)) {
            throw new FileCloudStorageException("Only jpg, jpeg, png, gif, bmp files are allowed");
        }
    }

    public static String getFileName(String name){
        return String.format(FILE_NAME_FORMAT, name);
    }
}
