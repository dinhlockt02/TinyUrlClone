package com.example.tinyurlclone.user;

import com.example.tinyurlclone.user.dto.UserReponseDto;
import com.example.tinyurlclone.user.model.User;
import com.example.tinyurlclone.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/self")
    public ResponseEntity<UserReponseDto> getSelf(@AuthenticationPrincipal UserDetails currentUser) {
        User self = userService.findById(Long.parseLong(currentUser.getUsername())).orElse(null);
        return new ResponseEntity<>(UserReponseDto.fromUser(self), HttpStatus.OK);
    }
}
