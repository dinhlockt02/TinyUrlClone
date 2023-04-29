package com.example.tinyurlclone.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@ToString
@Jacksonized
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;
    @NotBlank
    @Size(min = 8, message = "password must be at least 8 characters")
    @JsonProperty("password")
    private String password;
}
