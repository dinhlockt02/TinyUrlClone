package com.example.tinyurlclone.auth.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AuthDTO {
    private String accessToken;
    private String refreshToken;
}
