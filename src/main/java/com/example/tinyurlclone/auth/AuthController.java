package com.example.tinyurlclone.auth;

import com.example.tinyurlclone.auth.model.AuthDTO;
import com.example.tinyurlclone.auth.model.LoginDTO;
import com.example.tinyurlclone.auth.model.RegisterDTO;
import com.example.tinyurlclone.auth.service.AuthService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@Validated
public class AuthController {
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthDTO> register(@Valid @RequestBody RegisterDTO dto) {
        var authDto = service.register(dto);
        return ResponseEntity.ok(authDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@Valid @RequestBody LoginDTO dto) {
        var authDto = service.login(dto);
        return ResponseEntity.ok(authDto);
    }
}
