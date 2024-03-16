package com.example.smallproject_rge_vta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.smallproject_rge_vta.dto.Restaurant;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Restaurant> restaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();

        FirestoreManager.getRestaurants(data -> {
            restaurants = (List<Restaurant>) data;
            showRestaurants(bundle);
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