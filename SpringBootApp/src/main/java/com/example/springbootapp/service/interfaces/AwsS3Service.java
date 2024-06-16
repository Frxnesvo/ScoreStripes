package com.example.springbootapp.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {
    String generatePresignedUrl(String url);
    String uploadFile(MultipartFile file, String folder, String name);
    String uploadFile(byte[] file, String folder, String name);
}
