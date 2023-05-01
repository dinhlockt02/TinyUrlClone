package com.example.tinyurlclone.url.dto;

import com.example.tinyurlclone.common.ObjectID;
import com.example.tinyurlclone.common.UID;
import com.example.tinyurlclone.url.model.Url;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UrlDto {

    private UID id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String alias;

    private String fullUrl;

    private Date createdAt;

    private Date updatedAt;

    public static UrlDto GetUrlDto(Url url) {
        if (url == null) {
            return null;
        }
        return UrlDto.builder()
                .id(new UID(url.getId(), ObjectID.URL))
                .fullUrl(url.getFullUrl())
                .alias(url.getAlias())
                .createdAt(url.getCreatedAt())
                .updatedAt(url.getUpdatedAt())
                .build();
    }
}
