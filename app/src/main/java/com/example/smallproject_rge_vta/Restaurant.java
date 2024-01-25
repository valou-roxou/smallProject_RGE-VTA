package com.example.smallproject_rge_vta;

public class Restaurant {
    private String name;
    private float stars;
    private int location;

    public Restaurant(String name, float stars, int location) {
        this.name = name;
        this.stars = stars;
        this.location = location;
    }

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
