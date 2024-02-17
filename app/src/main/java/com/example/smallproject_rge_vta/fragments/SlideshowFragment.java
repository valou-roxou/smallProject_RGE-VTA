package com.example.smallproject_rge_vta.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.smallproject_rge_vta.R;

public class SlideshowFragment extends Fragment {

    private ConstraintLayout constraintLayout;

    public SlideshowFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slideshow, container, false);

        constraintLayout = rootView.findViewById(R.id.slideshow_constraint_layout);
        constraintLayout.setBackground(AppCompatResources.getDrawable(rootView.getContext(), R.drawable.restaurant_ui_ux));

        return rootView;
    }
}
