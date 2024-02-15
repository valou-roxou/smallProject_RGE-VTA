package com.example.smallproject_rge_vta;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smallproject_rge_vta.fragments.LogoDecoratorFragment;

public class PlaygroundLogoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground_logo);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.logo_fragment_container,
                        new LogoDecoratorFragment()
                ).commit();
    }
}
