package com.example.tinyurlclone.url.service;

import com.example.tinyurlclone.url.dto.CreateUrlDto;
import com.example.tinyurlclone.url.dto.UrlDto;

public interface UrlService {
    UrlDto createUrl(CreateUrlDto dto);
    UrlDto findUrlById(Long id);
    UrlDto findUrlByAlias(String alias);
}
