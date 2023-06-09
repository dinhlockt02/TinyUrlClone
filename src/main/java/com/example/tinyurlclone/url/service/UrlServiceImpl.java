package com.example.tinyurlclone.url.service;

import com.example.tinyurlclone.common.ObjectID;
import com.example.tinyurlclone.common.UID;
import com.example.tinyurlclone.exception.ConflictException;
import com.example.tinyurlclone.kgs.service.KgService;
import com.example.tinyurlclone.url.dto.CreateUrlDto;
import com.example.tinyurlclone.url.dto.UrlDto;
import com.example.tinyurlclone.url.model.Url;
import com.example.tinyurlclone.url.repository.RedisUrlRepository;
import com.example.tinyurlclone.url.repository.UrlRepository;
import com.example.tinyurlclone.user.model.User;
import com.example.tinyurlclone.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UrlServiceImpl implements UrlService{

    private final UrlRepository urlRepository;
    private final UserService userService;
    private final RedisUrlRepository redisRepository;
    private final KgService kgService;

    @Override
    @Transactional
    public UrlDto createUrl(CreateUrlDto dto) {
        User user = null;

        if (dto.getAlias() == null) {
            boolean isUsed = true;
            while(isUsed) {
                String key = kgService.Get();
                dto.setAlias(key);
                Url existedUrl = urlRepository.findByAlias(dto.getAlias()).orElse(null);
                if (existedUrl == null) {
                    isUsed = false;
                }
            }
        } else {
            Url existedUrl = urlRepository.findByAlias(dto.getAlias()).orElse(null);
            if (existedUrl != null) {
                throw new ConflictException("alias existed");
            }
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
        UrlDto urlDto = UrlDto.GetUrlDto(url);

        return UrlDto.GetUrlDto(url);
    }

    @Override
    public UrlDto findUrlByAlias(String alias) {
        Optional<UrlDto> urlDto = redisRepository.getUrl(alias);
        if (urlDto.isPresent()) {
            return urlDto.get();
        }
        var url = urlRepository.findByAlias(alias).orElse(null);
        if (url == null) {
            return null;
        }
        UrlDto dto = UrlDto.GetUrlDto(url);
        redisRepository.setUrl(dto);
        return dto;
    }
}
