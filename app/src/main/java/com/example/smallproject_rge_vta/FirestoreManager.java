package com.example.smallproject_rge_vta;

import android.util.Log;

import com.example.smallproject_rge_vta.dto.Feedback;
import com.example.smallproject_rge_vta.dto.Restaurant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

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
                    Restaurant restaurant = new Restaurant(
                            document.getId(),
                            document.getString("name"),
                            document.getDouble("stars").floatValue(),
                            document.getLong("location").intValue()
                    );
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
        Feedback feedback = new Feedback(java.util.UUID.randomUUID().toString(),null, restaurant.getId(), text);

        docRef.add(feedback)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Feedback added with ID: " + documentReference.getId());
                    callback.onCallback(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding feedback", e);
                });
    }
}
