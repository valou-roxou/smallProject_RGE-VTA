package com.example.smallproject_rge_vta.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.smallproject_rge_vta.R;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private ConstraintLayout constraintLayout;

    private List<Drawable> pictures;

    private Button leftButton;

    private Button rightButton;

    private int indexPicture;

    private int sizePictures;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slideshow, container, false);

        constraintLayout = rootView.findViewById(R.id.slideshow_constraint_layout);

        leftButton = rootView.findViewById(R.id.leftCustomButton);
        leftButton.setOnClickListener(previousPicture);

        rightButton = rootView.findViewById(R.id.rightCustomButton);
        rightButton.setOnClickListener(nextPicture);

        // --- TODO appel base de données
        ArrayList<Drawable> pictures = new ArrayList<>();
        pictures.add(AppCompatResources.getDrawable(this.getContext(), R.drawable.restaurant_ui_ux));
        pictures.add(AppCompatResources.getDrawable(this.getContext(), R.drawable.restaurant));
        this.pictures = pictures;

        indexPicture = 0;
        sizePictures = pictures.size();

        constraintLayout.setBackground(pictures.get(indexPicture));
        // ---

        return rootView;
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