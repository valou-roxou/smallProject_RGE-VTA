package com.example.smallproject_rge_vta;

import android.util.Log;

import com.example.smallproject_rge_vta.dto.Feedback;
import com.example.smallproject_rge_vta.dto.Picture;
import com.example.smallproject_rge_vta.dto.Reservation;
import com.example.smallproject_rge_vta.dto.Restaurant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
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
                    // Log.d(TAG, document.getId() + " => " + document.getData());
                    Restaurant restaurant = document.toObject(Restaurant.class);
                    restaurant.setId(document.getId());
                    restaurants.add(restaurant);
                }
                callback.onCallback(restaurants);
            } else {
                // Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    public static void getPicturesForRestaurant(FirestoreCallback callback, String restaurantId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference restaurantRef = db.collection("restaurant").document(restaurantId);

        // Get the list of feedback IDs from the restaurant document
        restaurantRef.get().addOnCompleteListener(restaurantTask -> {
            if (restaurantTask.isSuccessful()) {
                DocumentSnapshot restaurantDoc = restaurantTask.getResult();
                if (restaurantDoc.exists()) {
                    List<String> feedbackIds = (List<String>) restaurantDoc.get("feedbacks");
                    if (feedbackIds != null) {
                        for (String feedbackId : feedbackIds) {
                            // Reference to the feedback document
                            DocumentReference feedbackRef = db.collection("feedback").document(feedbackId);

                            // Get the list of picture IDs from the feedback document
                            feedbackRef.get().addOnCompleteListener(feedbackTask -> {
                                if (feedbackTask.isSuccessful()) {
                                    DocumentSnapshot feedbackDoc = feedbackTask.getResult();
                                    if (feedbackDoc.exists()) {
                                        List<String> pictureIds = (List<String>) feedbackDoc.get("pictures");
                                        if (pictureIds != null) {
                                            for (String pictureId : pictureIds) {
                                                // Retrieve the picture document from the "pictures" collection
                                                DocumentReference pictureDocRef = db.collection("picture").document(pictureId);
                                                pictureDocRef.get().addOnCompleteListener(pictureTask -> {
                                                    if (pictureTask.isSuccessful()) {
                                                        DocumentSnapshot pictureDoc = pictureTask.getResult();
                                                        if (pictureDoc.exists()) {
                                                            // Picture document exists, you can retrieve the picture object
                                                            Picture picture = pictureDoc.toObject(Picture.class);
                                                            callback.onCallback(picture);
                                                            // Create Picture object or do something with contentB64
                                                        } else {
                                                            // Log.d("Firestore", "No such picture document");
                                                        }
                                                    } else {
                                                        // Log.d("Firestore", "Error getting picture document: ", pictureTask.getException());
                                                    }
                                                });
                                            }
                                        }
                                    } else {
                                        // Log.d("Firestore", "No such feedback document");
                                    }
                                } else {
                                    // Log.d("Firestore", "Error getting feedback document: ", feedbackTask.getException());
                                }
                            });
                        }
                    }
                } else {
                    // Log.d("Firestore", "No such restaurant document");
                }
            } else {
                // Log.d("Firestore", "Error getting restaurant document: ", restaurantTask.getException());
            }
        });
    }

    public static void getPictureById(FirestoreCallback callback, String pictureId) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference docRef = database.collection("picture").document(pictureId);

        // Get the document by its ID
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    Picture picture = document.toObject(Picture.class);
                    assert picture != null;

                    picture.setId(document.getId());
                    callback.onCallback(picture);
                } else {
                    // Log.d(TAG, "No such document");
                    callback.onCallback(null);
                }
            } else {
                // Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    public static void postFeedback(FirestoreCallback callback, Restaurant restaurant, String text, List<String> imageData) {
        List<String> picturesId = postPictures(imageData);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference docRef = database.collection("feedback");
        String feedbackId = java.util.UUID.randomUUID().toString();
        Feedback feedback = new Feedback(feedbackId, picturesId, restaurant.getId(), text);

        docRef.document(feedbackId).set(feedback)
                .addOnSuccessListener(aVoid -> {
                    // Log.d(TAG, "Feedback added with ID: " + feedbackId);
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

    private static List<String> postPictures(List<String> imageData) {
        List<String> picturesId = new ArrayList<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference docRef = database.collection("picture");

        for(String imageDataB64 : imageData) {
            String pictureId = java.util.UUID.randomUUID().toString();
            picturesId.add(pictureId);
            Picture picture = new Picture(pictureId, imageDataB64);

            docRef.document(pictureId).set(picture)
                    .addOnSuccessListener(aVoid -> {
                        // Log.d(TAG, "Feedback added with ID: " + pictureId);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error adding feedback", e);
                    });
        }

        return picturesId;
    }

    private static void updateRestaurant(Restaurant restaurant, Map<String, Object> updates) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference restaurantRef = database.collection("restaurant");
        String restaurantId = restaurant.getId();

        restaurantRef.document(restaurantId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    // Log.d(TAG, "Restaurant updated successfully");
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
                    // Log.d(TAG, "Reservation added with ID: " + reservationId);
                    callback.onCallback(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding reservation", e);
                });

        restaurant.addReservation(reservationId);
        Map<String, Object> updates = new HashMap<>();
        updates.put("reservations", restaurant.getReservations());
        updateRestaurant(restaurant, updates);
    }

}
