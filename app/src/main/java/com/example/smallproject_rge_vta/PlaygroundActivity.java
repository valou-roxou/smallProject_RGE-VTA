package com.example.smallproject_rge_vta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smallproject_rge_vta.fragments.SlideshowFragment;

public class PlaygroundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
    }

    public void startSlideshow(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_restaurant", 0);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(R.id.playground_fragment_container, SlideshowFragment.class, bundle).commit();
    }

    public void startTakePicture(View view) {
        startActivity(new Intent(this, CameraActivity.class));
    }
}
