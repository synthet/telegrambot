package ru.synthet.telegrambot.integration.cats.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoteRequest implements Serializable {
    @JsonProperty("image_id")
    private String imageId;
    @JsonProperty("sub_id")
    private String subId;
    private int value;

    public VoteRequest(String imageId, String subId, boolean value) {
        this.imageId = imageId;
        this.subId = subId;
        this.value = value ? 1 : 0;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
