package com.example.tinyurlclone.cloud;

public interface CloudService {
    String uploadImage(byte[] fileBytes, String fileName);
    String uploadImage(byte[] fileBytes, String fileName, String contentType);
}
