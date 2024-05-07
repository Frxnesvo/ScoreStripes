package com.example.springbootapp.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {
    public String generatePresignedUrl(String url);
    public String uploadFile(MultipartFile file, String folder, String name);
}
