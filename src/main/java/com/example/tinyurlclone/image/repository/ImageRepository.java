package com.example.tinyurlclone.image.repository;

import com.example.tinyurlclone.image.model.Image;
import org.springframework.data.repository.Repository;

public interface ImageRepository extends Repository<Image, Long> {
    Image save(Image image);
}
