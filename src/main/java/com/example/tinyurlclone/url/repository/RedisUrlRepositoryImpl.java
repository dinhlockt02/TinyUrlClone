package com.example.tinyurlclone.url.repository;

import com.example.tinyurlclone.common.Const;
import com.example.tinyurlclone.common.ObjectID;
import com.example.tinyurlclone.common.UID;
import com.example.tinyurlclone.url.dto.UrlDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RedisUrlRepositoryImpl implements RedisUrlRepository {
    private final RedisTemplate<String, String> template;

    @Override
    public Optional<UrlDto> getUrl(String key) {
        try {
            key = Const.URL_KEY_PREFIX + key;
            String redisUrlDto =  template.opsForValue().get(key);
            if (redisUrlDto == null) {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            UrlDto urlDto = objectMapper.readValue(redisUrlDto, UrlDto.class);
            return Optional.of(urlDto);
        }catch ( JsonProcessingException e) {
            return Optional.empty();
        }
//        return Optional.empty();
    }

    @Override
    public void setUrl(UrlDto dto) {
        try {
            String uid = dto.getId().toString();
            ObjectMapper objectMapper = new ObjectMapper();
            String redisUrlDto = objectMapper.writeValueAsString(dto);
            template.opsForValue().set(Const.URL_KEY_PREFIX + uid, redisUrlDto);
            template.opsForValue().set(Const.URL_KEY_PREFIX + dto.getAlias(), redisUrlDto);
        } catch (JsonProcessingException ignored) {
        }
    }
}
