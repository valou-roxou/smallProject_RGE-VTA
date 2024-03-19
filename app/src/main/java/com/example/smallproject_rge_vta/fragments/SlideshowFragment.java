package com.example.smallproject_rge_vta.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

    private List<Drawable> pictures = new ArrayList<>();

    private int indexPicture;

    private int sizePictures = 0;

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
    }

    public void addImage(Bitmap bitmap) {
        pictures.add(new BitmapDrawable(getResources(), bitmap));
        constraintLayout.setBackground(pictures.get(sizePictures));
        sizePictures++;
    }

    public List<Drawable> getPictures() {
        return this.pictures;
    }

    private final View.OnClickListener previousPicture = v -> {
        if(sizePictures > 0) {
            if (--indexPicture < 0) {
                indexPicture = sizePictures - 1;
            }
            constraintLayout.setBackground(pictures.get(indexPicture));
        }
    };

    private final View.OnClickListener nextPicture = v -> {
        if(sizePictures > 0) {
            if (++indexPicture >= sizePictures) {
                indexPicture = 0;
            }
            constraintLayout.setBackground(pictures.get(indexPicture));
        }
    };
}