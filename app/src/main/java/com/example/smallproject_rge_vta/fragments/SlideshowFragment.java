package com.example.smallproject_rge_vta.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.smallproject_rge_vta.R;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private ConstraintLayout constraintLayout;

    private List<Drawable> pictures;

    private int indexPicture;

    private int sizePictures;

    public SlideshowFragment() {
        super(R.layout.fragment_slideshow);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        constraintLayout = view.findViewById(R.id.slideshow_constraint_layout);

        Button leftButton = view.findViewById(R.id.leftCustomButton);
        leftButton.setOnClickListener(previousPicture);

        Button rightButton = view.findViewById(R.id.rightCustomButton);
        rightButton.setOnClickListener(nextPicture);

        int idRestaurant = requireArguments().getInt("id_restaurant");
        searchRestaurant(idRestaurant);

    }

    private void searchRestaurant (int id) {
        // --- TODO: requête base de données
        ArrayList<Drawable> pictures = new ArrayList<>();
        pictures.add(AppCompatResources.getDrawable(this.getContext(), R.drawable.restaurant_ui_ux));
        pictures.add(AppCompatResources.getDrawable(this.getContext(), R.drawable.restaurant));
        // ---

        this.pictures = pictures;
        indexPicture = 0;
        sizePictures = this.pictures.size();

        constraintLayout.setBackground(this.pictures.get(indexPicture));
    }

    private final View.OnClickListener previousPicture = v -> {
        if (--indexPicture < 0) {
            indexPicture = sizePictures - 1;
        }
        constraintLayout.setBackground(pictures.get(indexPicture));
    };

    private final View.OnClickListener nextPicture = v -> {
        if (++indexPicture >= sizePictures) {
            indexPicture = 0;
        }
        constraintLayout.setBackground(pictures.get(indexPicture));
    };
}