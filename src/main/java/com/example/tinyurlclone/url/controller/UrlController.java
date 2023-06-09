package com.example.tinyurlclone.url.controller;

import com.example.tinyurlclone.common.ObjectID;
import com.example.tinyurlclone.common.UID;
import com.example.tinyurlclone.url.dto.CreateUrlDto;
import com.example.tinyurlclone.url.dto.UrlDto;
import com.example.tinyurlclone.url.service.UrlService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/url")
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<UrlDto> createUrl(
            @Valid @RequestBody CreateUrlDto dto,
            @AuthenticationPrincipal UserDetails user
            ) {

        if(user != null) {
            dto.setUserId(Long.valueOf(user.getUsername()));
        }

        UrlDto urlDto = urlService.createUrl(dto);

        return ResponseEntity.created(URI.create("/url/" + urlDto.getId().toString())).body(urlDto);
    }

    @GetMapping("/{alias}")
    public ResponseEntity<UrlDto> getUrl(
            @PathVariable("alias") String alias
    ) {
        UrlDto urlDto = urlService.findUrlByAlias(alias);

        return ResponseEntity.ok(urlDto);
    }
}
