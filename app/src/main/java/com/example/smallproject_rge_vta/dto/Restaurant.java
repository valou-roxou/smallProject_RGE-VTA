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
    private List<String> reservations;
    private String defaultPicture;
    private double lat;
    private double lng;

    public Restaurant() {}

    public Restaurant(String id, String name, float stars, int location, List<String> feedbacks, List<String> reservations, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.location = location;
        this.feedbacks = feedbacks;
        this.reservations = reservations;
        this.lat = lat;
        this.lng = lng;
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

    public List<String> getReservations() {
        return reservations;
    }

    public void setReservations(List<String> reservations) {
        this.reservations = reservations;
    }

    public void addFeedback(String feedbackId) {
        if(feedbacks == null) feedbacks = new ArrayList<>();
        feedbacks.add(feedbackId);
    }

    public void addReservation(String reservationId) {
        if(reservations == null) reservations = new ArrayList<>();
        reservations.add(reservationId);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDefaultPicture() {
        return defaultPicture;
    }

    public void setDefaultPicture(String defaultPicture) {
        this.defaultPicture = defaultPicture;
    }

}
