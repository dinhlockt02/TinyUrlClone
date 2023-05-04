package com.example.tinyurlclone.image.model;

import com.example.tinyurlclone.common.ObjectID;
import com.example.tinyurlclone.common.UID;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;

@Data
@Builder
@Jacksonized
public class ImageDto {

    @JsonIgnore
    private String urlPrefix;

    @JsonProperty("id")
    private UID uid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private ImageType type;
    @JsonGetter("url")
    public String getUrl() {
        return urlPrefix + name;
    }
    @JsonIgnore
    private Long ownerId;

    public static class ImageDtoBuilder {
        private UID uid;
        public ImageDtoBuilder id(Long id) {
            this.uid = new UID(id, ObjectID.IMAGE);
            return this;
        }
    }
}
