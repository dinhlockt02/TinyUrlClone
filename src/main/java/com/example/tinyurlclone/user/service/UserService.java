package com.example.tinyurlclone.user.service;

import com.example.tinyurlclone.user.model.User;

import java.util.Optional;

public interface UserService {
    User create(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
