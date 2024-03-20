package com.example.smallproject_rge_vta.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.smallproject_rge_vta.FirestoreManager;
import com.example.smallproject_rge_vta.R;
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

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private final HashMap<Marker, String> markerToRestaurantId = new HashMap<>();
    private Marker selectedMarker = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng univ = new LatLng(43.562005, 1.469607);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(univ, 10));

        FirestoreManager.getRestaurants(data -> {
            List<Restaurant> restaurants = (List<Restaurant>) data;

            for (Restaurant restaurant : restaurants) {
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
        if (selectedMarker == marker) return false;
        selectedMarker = marker;

        // Show the bottom drawer
        FragmentContainerView fcv = requireActivity().findViewById(R.id.bottom_drawer);
        fcv.setVisibility(View.VISIBLE);

        // Clear the horizontal layout
        LinearLayout hsv = requireActivity().findViewById(R.id.map_horizontal_layout);
        hsv.removeAllViews();

        // Fetch pictures for the selected restaurant
        FirestoreManager.getPicturesForRestaurant(data -> {
            // Handle picture data
            // Example code to create ImageView and add it to the horizontal layout
            Picture picture = (Picture) data;
            byte[] compressedData = Base64.decode(picture.getContentB64(), Base64.URL_SAFE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(compressedData, 0, compressedData.length);
            ImageView img = new ImageView(requireContext());
            img.setImageBitmap(bitmap);
            img.setAdjustViewBounds(true);
            hsv.addView(img);
        }, markerToRestaurantId.get(marker));

        return false;
    };

    private final GoogleMap.OnMapClickListener mapListener = marker -> {
        // Hide the bottom drawer
        requireActivity().findViewById(R.id.bottom_drawer).setVisibility(View.INVISIBLE);
    };
}
