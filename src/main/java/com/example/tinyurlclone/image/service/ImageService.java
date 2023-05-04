package com.example.tinyurlclone.image.service;


import com.example.tinyurlclone.image.model.Image;
import com.example.tinyurlclone.image.model.ImageDto;
import com.example.tinyurlclone.image.model.ImageType;
import com.example.tinyurlclone.user.model.User;
import org.springframework.beans.factory.annotation.Value;

public interface ImageService {
    Image upload(byte[] imageBytes, String name, String contentType, ImageType type, User owner);
    ImageDto getImageDto(Image image);

    Image getImage(Long id);
}
