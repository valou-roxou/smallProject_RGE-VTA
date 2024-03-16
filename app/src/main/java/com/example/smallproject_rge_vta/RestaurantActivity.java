package com.example.smallproject_rge_vta;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smallproject_rge_vta.dto.Restaurant;
import com.example.smallproject_rge_vta.fragments.FeedbackFragment;
import com.example.smallproject_rge_vta.fragments.SlideshowFragment;

import androidx.fragment.app.FragmentContainerView;

import com.example.smallproject_rge_vta.fragments.ReservationFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;

public class RestaurantActivity extends AppCompatActivity {

    private ImageView imageView;

    private TabLayout tabLayout;

    private FragmentContainerView fragmentContainerView;

    private Restaurant restaurant;

    public RestaurantActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        fragmentContainerView = findViewById(R.id.restaurant_fragment_container);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(tabListener);

        // Get the data passed when clicked
        Intent intent = getIntent();
        if (intent != null) {
            restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
        }

        // TODO: Implémenter le comportement des ongles "Menu" et "Avis"
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
        imageView = findViewById(R.id.take_picture_picture);
        cameraResultLauncher.launch(new Intent(this, CameraActivity.class));
    }

    public void onClickSavedAvis(View view) {
        TextView commentTextView = findViewById(R.id.comment_editText);
        String comment = commentTextView.getText().toString();
        FirestoreManager.postFeedback(data -> {
            // TODO : Ajouter un feedback pour dire que l'avis a été bien enregistré
        }, restaurant, comment);
    }

    private ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Si l'activité s'est bien terminé et qu'on a un résultat
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String uriPath = data.getStringExtra("uri_path");
                    // Si on a bien une uri
                    if(uriPath != null) {
                        // On ouvre le contenu associé à l'URI
                        try (InputStream inputStream = getContentResolver().openInputStream(Uri.parse(uriPath))){
                            // Affichage de l'image si on a son contenu
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                            // Rotation à 90 de l'image
                            Matrix matrix = new Matrix();
                            matrix.postRotate(90);
                            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                            imageView.setImageBitmap(rotatedBitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
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
                        return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        };
}
