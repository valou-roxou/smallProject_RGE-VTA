package com.example.smallproject_rge_vta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.smallproject_rge_vta.dto.Picture;
import com.example.smallproject_rge_vta.dto.Restaurant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String openedRestaurantId = "";
    private HashMap<Marker, String> markerToRestaurantId = new HashMap<>();
    private Marker selectedMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    static class MarkerOptionsPlus extends MarkerOptions {
        String restaurantId;
        public MarkerOptionsPlus(String restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getRestaurantId() {
            return restaurantId;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.

     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng univ = new LatLng(43.562005, 1.469607);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(univ));

        FirestoreManager.getRestaurants(data -> {
            List<Restaurant> restaurants = (List<Restaurant>) data;

            for(Restaurant restaurant: restaurants) {
                LatLng pos = new LatLng(restaurant.getLat(), restaurant.getLng());
                Marker m = mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title(restaurant.getName()));
                markerToRestaurantId.put(m, restaurant.getId());
            }

            mMap.setOnMarkerClickListener(markerListener);
            mMap.setOnMapClickListener(mapListener);
        });
    }

    private final GoogleMap.OnMarkerClickListener markerListener = marker -> {
        if(selectedMarker == marker) return false;
        selectedMarker = marker;

        FragmentContainerView fcv = findViewById(R.id.bottom_drawer);
        fcv.setVisibility(View.VISIBLE);
        ((LinearLayout)fcv.findViewById(R.id.map_horizontal_layout)).removeAllViews();

        FirestoreManager.getPicturesForRestaurant(data -> {
            Picture picture = (Picture) data;

            byte[] compressedData = Base64.decode(picture.contentB64, Base64.URL_SAFE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(compressedData, 0, compressedData.length);

            ImageView img = new ImageView(this.getBaseContext());
            img.setImageBitmap(bitmap);
            img.setAdjustViewBounds(true);

            LinearLayout hsv = findViewById(R.id.bottom_drawer).findViewById(R.id.map_horizontal_layout);
            hsv.addView(img);
        }, markerToRestaurantId.get(marker));

        return false;
    };

    private final GoogleMap.OnMapClickListener mapListener = marker -> {
        findViewById(R.id.bottom_drawer).setVisibility(View.INVISIBLE);
    };
}

