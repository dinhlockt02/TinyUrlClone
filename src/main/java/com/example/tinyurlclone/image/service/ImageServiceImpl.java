package com.example.tinyurlclone.image.service;

import com.example.tinyurlclone.cloud.CloudService;
import com.example.tinyurlclone.image.model.Image;
import com.example.tinyurlclone.image.model.ImageDto;
import com.example.tinyurlclone.image.model.ImageType;
import com.example.tinyurlclone.image.repository.ImageRepository;
import com.example.tinyurlclone.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService{

    private final CloudService cloudService;
    private final ImageRepository imageRepository;

    @Value("${application.image.url-prefix}")
    private String urlPrefix;

    @Override
    public Image upload(byte[] imageBytes, String name, String contentType, ImageType type, User owner) {
        name = cloudService.uploadImage(imageBytes, name, contentType);
        Image image = new Image(name, type, owner);
        image = imageRepository.save(image);
        return image;
    }

    @Override
    public ImageDto getImageDto(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .name(image.getName())
                .type(image.getType())
                .urlPrefix(urlPrefix)
                .ownerId(image.getOwner().getId())
                .build();
    }

    @Override
    public Image getImage(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
}
