package com.example.smallproject_rge_vta;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smallproject_rge_vta.fragments.SlideshowFragment;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Bundle bundle = new Bundle();
        bundle.putInt("id_restaurant", 0);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(R.id.restaurant_fragment_container, SlideshowFragment.class, bundle).commit();
    }
}
