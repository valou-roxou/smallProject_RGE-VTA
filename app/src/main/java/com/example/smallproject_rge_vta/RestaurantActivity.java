package com.example.smallproject_rge_vta;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smallproject_rge_vta.fragments.FeedbackFragment;
import com.example.smallproject_rge_vta.fragments.SlideshowFragment;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentContainerView;

import com.example.smallproject_rge_vta.fragments.ReservationFragment;
import com.google.android.material.tabs.TabLayout;

public class RestaurantActivity extends AppCompatActivity {

    private ImageView imageView;

    private FragmentContainerView fragmentContainerView;

    public RestaurantActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        fragmentContainerView = findViewById(R.id.restaurant_fragment_container);

        TabLayout tabLayout = findViewById(R.id.restaurant_tab_layout);
        tabLayout.addOnTabSelectedListener(tabListener);

        // TODO: Implémenter le comportement des ongles "Menu"
        tabLayout.getTabAt(0).view.setClickable(false);
        tabLayout.getTabAt(1).select();

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
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RestaurantActivity.this, new String[]{android.Manifest.permission.CAMERA}, 200);
        } else {
            imageView = findViewById(R.id.take_picture_picture);
            cameraResultLauncher.launch(new Intent(this, CameraActivity.class));
        }
    }

    public void startPictureActivity(String uriPath) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra("uri_path_picture", uriPath);
        pictureResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Si l'activité s'est bien terminé et qu'on a un résultat
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String uriPath = data.getStringExtra("uri_path_picture");
                    // Si on a bien une uri
                    if(uriPath != null) {
                        startPictureActivity(uriPath);
                    }
                }
            });


    private final ActivityResultLauncher<Intent> pictureResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Si l'activité s'est bien terminé et qu'on a un résultat
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String uriPath = data.getStringExtra("uri_path_custom_picture");
                    // Si on a bien une uri
                    if(uriPath != null) {
                        imageView.setImageURI(Uri.parse(uriPath));
                    }
                }
            });


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
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        };
}
