package com.example.tinyurlclone.url.dto;

import com.example.tinyurlclone.user.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

@Data
public class CreateUrlDto {
    @Nullable
    @JsonProperty("alias")
    private String alias;

    @NotBlank
    @URL
    @JsonProperty("full_url")
    private String fullUrl;

    private Long userId;
}

