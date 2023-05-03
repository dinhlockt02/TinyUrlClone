package com.example.tinyurlclone.image.service;


import com.example.tinyurlclone.image.model.Image;
import com.example.tinyurlclone.image.model.ImageDto;
import com.example.tinyurlclone.image.model.ImageType;
import com.example.tinyurlclone.user.model.User;

public interface ImageService {
    ImageDto upload(byte[] imageBytes, String name, String contentType, ImageType type, User owner);
}
