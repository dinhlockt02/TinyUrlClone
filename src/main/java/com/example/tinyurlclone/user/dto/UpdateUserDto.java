package com.example.tinyurlclone.user.dto;

import com.example.tinyurlclone.common.UID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserDto {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("avatar")
    private UID avatar;
}
