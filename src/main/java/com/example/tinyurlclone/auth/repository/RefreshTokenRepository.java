package com.example.tinyurlclone.auth.repository;

import com.example.tinyurlclone.auth.model.RefreshToken;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface RefreshTokenRepository extends Repository<RefreshToken, Long> {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findById(Long id);
}
