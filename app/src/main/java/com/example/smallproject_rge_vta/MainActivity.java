package com.example.smallproject_rge_vta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.smallproject_rge_vta.dto.Restaurant;
import com.example.smallproject_rge_vta.fragments.MapFragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Restaurant> restaurants;
    FragmentContainerView mapFragment;
    RecyclerView restaurantList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();

        TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        tabLayout.addOnTabSelectedListener(tabListener);

        restaurantList = findViewById(R.id.recycler_view);
        mapFragment = findViewById(R.id.restaurant_fragment_container);

        FirestoreManager.getRestaurants(data -> {
            restaurants = (List<Restaurant>) data;
            showRestaurants(bundle);

            startMapFragment();
        });
    }

    private void startMapFragment() {
        MapFragment mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.restaurant_fragment_container, mapFragment)
                .commit();
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

    private final TabLayout.OnTabSelectedListener tabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()){

                // Avis
                case 0:
                    showList();
                    return;

                // Reservation
                case 1:
                    showMap();
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}

        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    };

    private void showMap() {
        mapFragment.setVisibility(View.VISIBLE);
        restaurantList.setVisibility(View.INVISIBLE);
    }

    private void showList() {
        mapFragment.setVisibility(View.INVISIBLE);
        restaurantList.setVisibility(View.VISIBLE);
    }
}