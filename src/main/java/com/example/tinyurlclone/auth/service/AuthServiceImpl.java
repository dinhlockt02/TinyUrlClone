package com.example.tinyurlclone.auth.service;

import com.example.tinyurlclone.auth.model.AuthDTO;
import com.example.tinyurlclone.auth.model.LoginDTO;
import com.example.tinyurlclone.auth.model.RefreshToken;
import com.example.tinyurlclone.auth.model.RegisterDTO;
import com.example.tinyurlclone.auth.repository.RefreshTokenRepository;
import com.example.tinyurlclone.common.JwtTokenService;
import com.example.tinyurlclone.common.ObjectID;
import com.example.tinyurlclone.common.UID;
import com.example.tinyurlclone.exception.NotFoundException;
import com.example.tinyurlclone.exception.UnauthenticatedException;
import com.example.tinyurlclone.user.model.User;
import com.example.tinyurlclone.user.repositoy.UserRepository;
import com.example.tinyurlclone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${application.security.token.refresh.expiration}")
    private Long refreshTokenExpiration;
    @Value("${application.security.token.access.expiration}")
    private Long accessTokenExpiration;
    private final UserRepository userRepository;

    @Override
    public AuthDTO register(RegisterDTO registerDTO) {

        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());

        User user = User.builder()
                .email(registerDTO.getEmail())
                .password(encodedPassword)
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .build();
        user = userService.save(user);


        String accessToken = getAccessTokenFromUser(user);

        String refreshToken = getRefreshTokenFromUser(user);

        return AuthDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthDTO login(LoginDTO loginDTO) throws UnauthenticatedException {


        var user = userService.findByEmail(loginDTO.getEmail()).orElseThrow(NotFoundException::new);

        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());

        if (!matches) {
            throw new UnauthenticatedException();
        }


        var accessToken = getAccessTokenFromUser(user);
        var refreshToken = getRefreshTokenFromUser(user);

        return AuthDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public RefreshToken findRefreshTokenById(Long id) {
        return refreshTokenRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public String getAccessTokenFromUser(User user) {
        Date ate = new Date(System.currentTimeMillis() + accessTokenExpiration);
        return jwtTokenService.createToken(new UID(user.getId(), ObjectID.USER).toString(),  ate,null);
    }

    private String getRefreshTokenFromUser(User user) {

        Date rte = new Date(System.currentTimeMillis() + refreshTokenExpiration);

        RefreshToken rt = RefreshToken.builder()
                .expiration(rte)
                .revoked(false)
                .user(user)
                .build();

        rt = refreshTokenRepository.save(rt);

        return jwtTokenService.createToken(new UID(rt.getId(), ObjectID.REFRESH_TOKEN).toString(), rte, null);
    }
}
