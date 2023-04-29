package com.example.tinyurlclone.user.dto;

import com.example.tinyurlclone.user.model.User;
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

    public static UserReponseDto fromUser(User user) {
        if (user == null) return null;
        return UserReponseDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
