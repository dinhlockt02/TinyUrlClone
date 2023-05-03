package com.example.tinyurlclone.cloud.aws;

import com.example.tinyurlclone.cloud.CloudService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;

@Service
@RequiredArgsConstructor
public class AwsService implements CloudService {

    private final S3Client s3Client;



    @Value("${application.aws.s3.bucket.name}")
    private String bucketName;
    public String uploadImage(byte[] fileBytes, String fileName) {
        String contentType;
        if (fileName.contains("png")) {
            contentType = "image/png";
        } else if (fileName.contains(".jpg") || fileName.contains(".jpeg")){
            contentType = "image/jpeg";
        } else {
            contentType = "application/octet-stream";
        }
        return uploadImage(fileBytes, fileName, contentType);
    }

    @Override
    public String uploadImage(byte[] fileBytes, String fileName, String contentType) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .key(fileName)
                .bucket(bucketName)
                .contentType(contentType)
                .build();

        PutObjectResponse response =  s3Client.putObject(objectRequest, RequestBody.fromBytes(fileBytes));
        return fileName;
    }
}
