package com.example.tinyurlclone.kgs.repository;

import com.example.tinyurlclone.kgs.model.Key;
import com.example.tinyurlclone.url.model.Url;
import org.springframework.data.repository.Repository;


public interface KeyRepository extends Repository<Key, Long> {
    Key save(Key key);
}
