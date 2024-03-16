package com.example.smallproject_rge_vta.dto;

import java.util.List;

public class Feedback {
    public String id;
    public List<String> pictures;
    public String restaurantId;
    public String text;
    public String userId;

    public Feedback() {}

    public Feedback(String id, List<String> pictures, String restaurantId, String text, String userId) {
        this.id = id;
        this.pictures = pictures;
        this.restaurantId = restaurantId;
        this.text = text;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
