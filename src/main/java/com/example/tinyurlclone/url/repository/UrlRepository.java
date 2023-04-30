package com.example.tinyurlclone.url.repository;

import com.example.tinyurlclone.url.model.Url;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UrlRepository extends Repository<Url, Long> {
    Optional<Url> findById(Long id);
    Optional<Url> findByAlias(String alias);
    Url save(Url url);
}
