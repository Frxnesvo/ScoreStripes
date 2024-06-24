package com.example.springbootapp.service.impl;

import com.example.springbootapp.exceptions.S3PutObjectException;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

    private final S3Presigner s3Presigner;
    private final S3Client s3Client;
    private final String bucketName="scorestripespics";

    public String generatePresignedUrl(String url){  //Mi garantisce sicurezza e privacy per non mandare direttamente l'url dell'immagine al client
        String[] urlParts = url.replace("https://", "").split("/", 2);
        String key = urlParts[1];


        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        int expirationMinutes = 60;     //TODO
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(expirationMinutes))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(getObjectPresignRequest).url().toString();
    }

    @Override
    public String uploadFile(MultipartFile file, String folder, String name) {
        name=name.replace(" ", "_");
        String key = folder + "/" + name;
        PutObjectRequest putRequest =PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();
        try {
            s3Client.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new S3PutObjectException("Error uploading file to S3");
        }
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toExternalForm();
    }

    @Override
    public String uploadFile(byte[] file, String folder, String name){
        String key = folder + "/" + name;
        PutObjectRequest putRequest =PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/png")
                .build();
        s3Client.putObject(putRequest, RequestBody.fromBytes(file));
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toExternalForm();
    }
}
