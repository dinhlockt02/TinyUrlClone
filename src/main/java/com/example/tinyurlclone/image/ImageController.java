package com.example.tinyurlclone.image;

import com.example.tinyurlclone.cloud.CloudService;
import com.example.tinyurlclone.exception.BadRequestException;
import com.example.tinyurlclone.image.model.ImageDto;
import com.example.tinyurlclone.image.model.ImageType;
import com.example.tinyurlclone.image.service.ImageService;
import com.example.tinyurlclone.security.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/image")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageDto> uploadImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam("type") String type,
            @AuthenticationPrincipal User user) throws IOException {
        try {
            String filename = user.getId() + "/" + new Date().getTime() + extractFilenameExt(file.getOriginalFilename(), true);
            byte[] fileBytes = file.getBytes();
            String contentType = file.getContentType();
            ImageType imageType = ImageType.valueOf(type.toUpperCase());
            ImageDto imageDto = imageService.upload(fileBytes, filename, contentType, imageType, user.getInnerUser());
            return ResponseEntity.status(HttpStatus.CREATED).body(imageDto);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private String extractFilenameExt(String filename, boolean dotIncluded) {
        if (filename == null) {
            return "";
        }
        if (!filename.contains(".")) {
            return filename;
        }
        String[] splitedFilename = filename.split("\\.");
        return (dotIncluded ? "." : "" ) + splitedFilename[splitedFilename.length - 1];
    }
}
