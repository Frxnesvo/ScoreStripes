package com.example.springbootapp.service.interfaces;

public interface AwsS3Service {
    public String generatePresignedUrl(String url);
}
