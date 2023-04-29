package com.example.tinyurlclone.user.repositoy;

import com.example.tinyurlclone.user.model.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    User save(User user);
}
