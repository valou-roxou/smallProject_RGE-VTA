package com.example.smallproject_rge_vta;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smallproject_rge_vta.dto.Picture;
import com.example.smallproject_rge_vta.dto.Restaurant;
import com.example.smallproject_rge_vta.fragments.FeedbackFragment;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentContainerView;

import com.example.smallproject_rge_vta.fragments.ReservationFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    private FeedbackFragment feedbackFragment;

    private FragmentContainerView fragmentContainerView;

    private ImageView imageView;

    private Restaurant restaurant;

    public RestaurantActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        fragmentContainerView = findViewById(R.id.restaurant_fragment_container);

        TextView nameView = findViewById(R.id.name_view);

        imageView = findViewById(R.id.restaurant_picture);

        TabLayout tabLayout = findViewById(R.id.restaurant_tab_layout);
        tabLayout.addOnTabSelectedListener(tabListener);

        // Get the data passed when clicked
        Intent intent = getIntent();
        if (intent != null) {
            restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
            if(restaurant != null) {
                nameView.setText(restaurant.getName());
                FirestoreManager.getPictureById(data -> {
                    Picture picture = (Picture) data;
                    byte[] compressedData = Base64.decode(picture.contentB64, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(compressedData, 0, compressedData.length);
                    imageView.setImageBitmap(bitmap);
                }, restaurant.getDefaultPicture());
            }
        }

        tabLayout.getTabAt(0).select();
        startFragementFeedback(this.getCurrentFocus());
    }

    public void startFragementReservation(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.restaurant_fragment_container, new ReservationFragment()).commit();
    }

    public void startFragementFeedback(View view) {
        feedbackFragment = new FeedbackFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.restaurant_fragment_container, feedbackFragment).commit();
    }

    public void startCameraActivity (View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RestaurantActivity.this, new String[]{android.Manifest.permission.CAMERA}, 200);
        } else {
            cameraResultLauncher.launch(new Intent(this, CameraActivity.class));
        }
    }

    public void startPictureActivity(String uriPath) {
        Intent intent = new Intent(this, PictureCustomizationActivity.class);
        intent.putExtra("uri_path_picture", uriPath);
        pictureResultLauncher.launch(intent);
    }

    public void onClickSavedFeedback(View view) {
        TextView commentTextView = findViewById(R.id.comment_editText);
        String comment = commentTextView.getText().toString();

        List<Bitmap> images = feedbackFragment.getSlideshowFragment().getPictures();
        List<String> imageData = new ArrayList<>();
        if(images != null) {
            for(Bitmap image : images) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] data = baos.toByteArray();
                String imageB64 = Base64.encodeToString(data, Base64.URL_SAFE);
                imageData.add(imageB64);
            }
        }

        FirestoreManager.postFeedback(data -> {
            String textPopUp = getString(R.string.feedback_saved_pop_up, restaurant.getName());

            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("pop_up_success", textPopUp);
            startActivity(intent);
        }, restaurant, comment, imageData);
    }

    public void onClickSavedReservation(View view) {
        EditText dateEditText = findViewById(R.id.date_editText);
        String date = dateEditText.getText().toString();

        EditText nbGuestsEditText = findViewById(R.id.guests_editText);
        String nbGuests = nbGuestsEditText.getText().toString();

        FirestoreManager.postReservation(data -> {
            String textPopUp = getString(R.string.reservation_book_pop_up, restaurant.getName(), date, nbGuests);

            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("pop_up_success", textPopUp);

            startActivity(intent);
        }, restaurant, date, nbGuests);
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
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(uriPath));
                            feedbackFragment.getSlideshowFragment().addImage(bitmap);
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

                    // Avis
                    case 0:
                        startFragementFeedback(fragmentContainerView);
                        return;

                    // Reservation
                    case 1:
                        startFragementReservation(fragmentContainerView);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        };
}
