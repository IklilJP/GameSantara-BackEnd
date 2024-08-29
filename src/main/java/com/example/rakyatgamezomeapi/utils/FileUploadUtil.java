package com.example.rakyatgamezomeapi.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUploadUtil {
    public static final long MAX_FILE_SIZE = 2*1024*1024;

    public static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    public static final String FILE_NAME_FORMAT = "%s_%s_%s";

    public static boolean isAllowedExtension(final String fileName, final String pattern){
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

    public static void assertAllowedExtension(MultipartFile file, String pattern){
        if(file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("File is oversize");
        }
        if (!isAllowedExtension(file.getOriginalFilename(), pattern)) {
            throw new AssertionError("Only jpg, png, gif, bmp files are allowed");
        }
    }

    public static String getFileName(String name){
        String format = String.format(FILE_NAME_FORMAT, name);
        return format;
    }
}
