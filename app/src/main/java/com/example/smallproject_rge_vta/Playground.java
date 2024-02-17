package com.example.smallproject_rge_vta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.example.smallproject_rge_vta.fragments.SlideshowFragment;

public class Playground extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
    }

    public void startSlideshow(View view) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.playground_fragment_container, new SlideshowFragment()).commit();
    }
}
