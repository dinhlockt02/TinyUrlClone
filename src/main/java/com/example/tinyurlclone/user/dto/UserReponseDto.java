package com.example.tinyurlclone.user.dto;

import com.example.tinyurlclone.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserReponseDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String avatar;

    public static UserReponseDto fromUser(User user, String avatar) {
        if (user == null) return null;
        return UserReponseDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(avatar)
                .build();
    }
}
