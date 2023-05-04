package com.example.tinyurlclone.user;

import com.example.tinyurlclone.exception.ForbiddenException;
import com.example.tinyurlclone.image.service.ImageService;
import com.example.tinyurlclone.user.dto.UpdateUserDto;
import com.example.tinyurlclone.user.dto.UserReponseDto;
import com.example.tinyurlclone.user.model.User;
import com.example.tinyurlclone.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("/self")
    public ResponseEntity<UserReponseDto> getSelf(@AuthenticationPrincipal UserDetails currentUser) {
        User self = userService.findById(Long.parseLong(currentUser.getUsername())).orElse(null);
        if (self == null) {
            return ResponseEntity.notFound().build();
        }
        String avatar;
        if (self.getImage() == null) avatar = null;
        else avatar = imageService.getImageDto(self.getImage()).toString();

        return new ResponseEntity<>(UserReponseDto.fromUser(self, avatar), HttpStatus.OK);
    }

    @PatchMapping("/self")
    public ResponseEntity<UserReponseDto> updateSelf(@AuthenticationPrincipal UserDetails currentUser, @RequestBody UpdateUserDto updateUserDto) {
        var selfId = Long.parseLong(currentUser.getUsername());
        User self = userService.findById(selfId).orElse(null);
        if (self == null) return ResponseEntity.notFound().build();
        if (updateUserDto.getFirstName() != null) self.setFirstName(updateUserDto.getFirstName());
        if (updateUserDto.getLastName() != null) self.setLastName(updateUserDto.getLastName());
        if (updateUserDto.getAvatar() != null) {
            var image = imageService.getImage(updateUserDto.getAvatar().getLocalID());
            if (image.getOwner().getId() != selfId) {
                throw new ForbiddenException("User is not owner of image");
            }
            self.setImage(image);
        }
        userService.save(self);

        String avatar;
        if (self.getImage() == null) avatar = null;
        else avatar = imageService.getImageDto(self.getImage()).getUrl();
        return ResponseEntity.ok(UserReponseDto.fromUser(self, avatar));
    }
}
