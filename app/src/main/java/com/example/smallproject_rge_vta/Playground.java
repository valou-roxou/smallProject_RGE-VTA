package com.example.smallproject_rge_vta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Playground extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
    }

    public void startActivityLogo(View view) {
        startActivity(new Intent(this, PlaygroundLogoActivity.class));
    }
}
