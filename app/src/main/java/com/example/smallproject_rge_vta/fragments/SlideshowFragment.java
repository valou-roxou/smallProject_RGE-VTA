package com.example.smallproject_rge_vta.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;import androidx.fragment.app.Fragment;

import com.example.smallproject_rge_vta.R;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private List<Bitmap> pictures = new ArrayList<>();

    private int indexPicture = 0;

    private int sizePictures = 0;

    private ImageView imageView;

    public SlideshowFragment() {
        super(R.layout.fragment_slideshow);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.slideshow_picture);

        Button leftButton = view.findViewById(R.id.leftCustomButton);
        leftButton.setOnClickListener(previousPicture);

        Button rightButton = view.findViewById(R.id.rightCustomButton);
        rightButton.setOnClickListener(nextPicture);
    }

    public void addImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        pictures.add(bitmap);
        indexPicture = ++sizePictures;
    }

    public List<Bitmap> getPictures() {
        return this.pictures;
    }

    private final View.OnClickListener previousPicture = v -> {
        if(sizePictures > 0) {
            if (--indexPicture < 0) {
                indexPicture = sizePictures - 1;
            }
            imageView.setImageBitmap(pictures.get(indexPicture));
        }
    };

    private final View.OnClickListener nextPicture = v -> {
        if(sizePictures > 0) {
            if (++indexPicture >= sizePictures) {
                indexPicture = 0;
            }
            imageView.setImageBitmap(pictures.get(indexPicture));
        }
    };
}