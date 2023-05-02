package com.example.tinyurlclone.kgs.service;

import com.example.tinyurlclone.kgs.model.Key;
import com.example.tinyurlclone.kgs.repository.KeyRepository;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class KgServiceImpl implements KgService {

    private final KeyRepository keyRepository;

    private final int size;

    private final static String redisKeysList = "kgs:keys:list";

    private final ListOperations<String, String> listOperations;

    public KgServiceImpl(
            KeyRepository keyRepository,
            RedisTemplate<String, String> redisTemplate,
            @Value("${application.kgs.size}") int size) {
        this.keyRepository = keyRepository;
        this.size = size;
        this.listOperations = redisTemplate.opsForList();

        for (int i = 0; i < this.size; i++) {
            addNewKeyToQueue();
        }
    }

    @Override
    public String Get() {
        String key = listOperations.leftPop(redisKeysList);
        Thread thread = new Thread(this::addNewKeyToQueue);
        thread.start();
        return key;
    }

    private void addNewKeyToQueue() {
        Key key = new Key();
        key = keyRepository.save(key);
        listOperations.rightPush(redisKeysList, key.toString());
    }
}
