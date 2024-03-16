package com.example.smallproject_rge_vta.dto;

import java.util.List;

public class User {
    public String id;
    public List<Feedback> avis;
    public String name;
    public List<Reservation> reservations;

    public User(String id, List<Feedback> avis, String name, List<Reservation> reservations) {
        this.id = id;
        this.avis = avis;
        this.name = name;
        this.reservations = reservations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Feedback> getAvis() {
        return avis;
    }

    public void setAvis(List<Feedback> avis) {
        this.avis = avis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
