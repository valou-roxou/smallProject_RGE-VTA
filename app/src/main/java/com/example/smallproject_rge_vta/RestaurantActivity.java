package com.example.smallproject_rge_vta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smallproject_rge_vta.fragments.FeedbackFragment;
import com.example.smallproject_rge_vta.fragments.SlideshowFragment;

import androidx.fragment.app.FragmentContainerView;

import com.example.smallproject_rge_vta.fragments.ReservationFragment;
import com.google.android.material.tabs.TabLayout;

public class RestaurantActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    private FragmentContainerView fragmentContainerView;

    public RestaurantActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        fragmentContainerView = findViewById(R.id.restaurant_fragment_container);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(tabListener);

        // TODO: Implémenter le comportement des ongles "Menu" et "Avis"
        tabLayout.getTabAt(0).view.setClickable(false);
        tabLayout.getTabAt(2).select();

        // Slideshow
        Bundle bundle = new Bundle();
        bundle.putInt("id_restaurant", 0);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .replace(R.id.restaurant_slideshow_fragment_container, SlideshowFragment.class, bundle).commit();

    }

    public void startFragementReservation(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.restaurant_fragment_container, new ReservationFragment()).commit();
    }

    public void startFragementFeedback(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.restaurant_fragment_container, new FeedbackFragment()).commit();
    }

    public void startCameraActivity (View view) {
        startActivity(new Intent(this, CameraActivity.class));
    }

    private final TabLayout.OnTabSelectedListener tabListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){

                    // Menu
                    case 0:
                        return;

                    // Avis
                    case 1:
                        startFragementFeedback(fragmentContainerView);
                        return;

                    // Reservation
                    case 2:
                        startFragementReservation(fragmentContainerView);
                        return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        };
}
