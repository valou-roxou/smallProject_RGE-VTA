package com.example.smallproject_rge_vta;

import android.util.Log;

import com.example.smallproject_rge_vta.dto.Feedback;
import com.example.smallproject_rge_vta.dto.Reservation;
import com.example.smallproject_rge_vta.dto.Restaurant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreManager {
    private static final String TAG = "firebase";

    public interface FirestoreCallback {
        void onCallback(Object data);
    }

    public static void getRestaurants(FirestoreCallback callback) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference docRef = database.collection("restaurant");

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Restaurant> restaurants = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    Restaurant restaurant = document.toObject(Restaurant.class);
                    restaurant.setId(document.getId());
                    restaurants.add(restaurant);
                }
                callback.onCallback(restaurants);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    public static void postFeedback(FirestoreCallback callback, Restaurant restaurant, String text) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference docRef = database.collection("feedback");
        String feedbackId = java.util.UUID.randomUUID().toString();
        Feedback feedback = new Feedback(feedbackId,null, restaurant.getId(), text);

        docRef.document(feedbackId).set(feedback)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Feedback added with ID: " + feedbackId);
                    callback.onCallback(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding feedback", e);
                });

        restaurant.addFeedback(feedbackId);

        Map<String, Object> updates = new HashMap<>();
        updates.put("feedbacks", restaurant.getFeedbacks());
        updateRestaurant(restaurant, updates);
    }

    private static void updateRestaurant(Restaurant restaurant, Map<String, Object> updates) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference restaurantRef = database.collection("restaurant");
        String restaurantId = restaurant.getId();

        restaurantRef.document(restaurantId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Restaurant updated successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating restaurant", e);
                });
    }


    public static void postReservation(FirestoreCallback callback, Restaurant restaurant, String date, String nbGuests) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference docRef = database.collection("reservation");
        String reservationId = java.util.UUID.randomUUID().toString();
        Reservation reservation = new Reservation(reservationId, date, Integer.parseInt(nbGuests), restaurant.getId());

        docRef.document(reservationId).set(reservation)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Reservation added with ID: " + reservationId);
                    callback.onCallback(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding reservation", e);
                });

        restaurant.addReservation(reservationId);
        Map<String, Object> updates = new HashMap<>();
        updates.put("reservations", restaurant.getFeedbacks());
        updateRestaurant(restaurant, updates);
    }

}
