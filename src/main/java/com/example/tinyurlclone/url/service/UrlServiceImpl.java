package com.example.tinyurlclone.url.service;

import com.example.tinyurlclone.exception.ConflictException;
import com.example.tinyurlclone.url.dto.CreateUrlDto;
import com.example.tinyurlclone.url.dto.UrlDto;
import com.example.tinyurlclone.url.model.Url;
import com.example.tinyurlclone.url.repository.UrlRepository;
import com.example.tinyurlclone.user.model.User;
import com.example.tinyurlclone.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class UrlServiceImpl implements UrlService{

    private final UrlRepository urlRepository;
    private UserService userService;

    @Override
    public UrlDto createUrl(CreateUrlDto dto) {
        User user = null;

        Url existedUrl = urlRepository.findByAlias(dto.getAlias()).orElse(null);
        if (existedUrl != null) {
            throw new ConflictException("alias existed");
        }

        if (dto.getUserId() != null) {
            user = userService.findById(dto.getUserId()).orElse(null);
        }

        Url url = Url.builder()
                .fullUrl(dto.getFullUrl())
                .createdAt(new Date())
                .updatedAt(new Date())
                .alias(dto.getAlias())
                .user(user)
                .build();
        url = urlRepository.save(url);
        return UrlDto.GetUrlDto(url);
    }

    @Override
    public UrlDto findUrlById(Long id) {
        var url = urlRepository.findById(id).orElse(null);
        return UrlDto.GetUrlDto(url);
    }

    @Override
    public UrlDto findUrlByAlias(String alias) {
        var url = urlRepository.findByAlias(alias).orElse(null);
        return UrlDto.GetUrlDto(url);
    }
}
