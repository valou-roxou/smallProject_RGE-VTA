package com.example.smallproject_rge_vta.dto;

import java.util.Date;

public class Reservation {
    public String id;
    public Date date;
    public int nbGuests;
    public String restaurantId;
    public User user;

    public Reservation(String id, Date date, int nbGuests, String restaurantId, User user) {
        this.id = id;
        this.date = date;
        this.nbGuests = nbGuests;
        this.restaurantId = restaurantId;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNbGuests() {
        return nbGuests;
    }

    public void setNbGuests(int nbGuests) {
        this.nbGuests = nbGuests;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
