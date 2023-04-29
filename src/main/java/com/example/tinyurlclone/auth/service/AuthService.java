package com.example.tinyurlclone.auth.service;

import com.example.tinyurlclone.auth.model.AuthDTO;
import com.example.tinyurlclone.auth.model.LoginDTO;
import com.example.tinyurlclone.auth.model.RefreshToken;
import com.example.tinyurlclone.auth.model.RegisterDTO;
import com.example.tinyurlclone.user.model.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthDTO register(RegisterDTO registerDTO);
    AuthDTO login(LoginDTO loginDTO);
    RefreshToken findRefreshTokenById(Long id);
    String getAccessTokenFromUser(User user);
}



