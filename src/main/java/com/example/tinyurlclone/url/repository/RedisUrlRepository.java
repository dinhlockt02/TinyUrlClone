package com.example.tinyurlclone.url.repository;

import com.example.tinyurlclone.url.dto.UrlDto;

import java.util.Optional;

public interface RedisUrlRepository {
    Optional<UrlDto> getUrl(String key);
    void setUrl(UrlDto dto);
}

