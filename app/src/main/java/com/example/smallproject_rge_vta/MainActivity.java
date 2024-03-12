package com.example.smallproject_rge_vta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore database;
    List<Restaurant> restaurants = new ArrayList<>();
    String TAG = "firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();

        database =  FirebaseFirestore.getInstance();
        CollectionReference docRef = database.collection("restaurant");

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    Restaurant restaurant = new Restaurant(
                            document.getString("name"),
                            document.getDouble("stars").floatValue(),
                            document.getLong("location").intValue()
                    );
                    restaurants.add(restaurant);
                }

                showRestaurants(bundle);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_playground) {
            startActivity(new Intent(this, PlaygroundActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showRestaurants(Bundle bundle) {
        RecyclerView recyclerView = this.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new CustomRecyclerViewAdapter(this, restaurants));

        // RecyclerView scroll vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(bundle != null) {
            String popUpSuccess = bundle.getString("pop_up_success");
            if(popUpSuccess != null) {
                CustomSnackbar.make(findViewById(android.R.id.content), popUpSuccess, BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }
    }
}