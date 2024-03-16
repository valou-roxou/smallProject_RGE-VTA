package com.example.smallproject_rge_vta.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {
    private String id;
    private String name;
    private float stars;
    private int location;
    private List<String> feedbacks;

    public Restaurant() {}

    public Restaurant(String id, String name, float stars, int location, List<String> feedbacks) {
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.location = location;
        this.feedbacks = feedbacks;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public List<String> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<String> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public void addFeedback(String feedbackId) {
        if(feedbacks == null) feedbacks = new ArrayList<>();
        feedbacks.add(feedbackId);
    }
}
