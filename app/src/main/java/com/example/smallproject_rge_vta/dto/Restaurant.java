package com.example.smallproject_rge_vta.dto;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private final String id;
    private String name;
    private float stars;
    private int location;

    public Restaurant(String id, String name, float stars, int location) {
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.location = location;
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
}
