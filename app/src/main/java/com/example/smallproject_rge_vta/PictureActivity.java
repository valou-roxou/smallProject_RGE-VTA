package com.example.smallproject_rge_vta;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.example.smallproject_rge_vta.fragments.FilterFragment;
import com.example.smallproject_rge_vta.fragments.StickerFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.Date;

public class PictureActivity extends AppCompatActivity {

    private ImageView imageView;

    private FragmentContainerView fragmentContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        fragmentContainerView = findViewById(R.id.filters_stickers_fragment_container);

        TabLayout tabLayout = findViewById(R.id.picture_tab_layout);
        tabLayout.addOnTabSelectedListener(tabListener);
        tabLayout.getTabAt(0).select();

        imageView = findViewById(R.id.filter_picture_picture);
        imageView.setDrawingCacheEnabled(true);

        Intent intent = getIntent();
        if(intent != null) {
            String uriPathPicture = intent.getStringExtra("uri_path_picture");
            // Si on a bien une uri
            if(uriPathPicture != null) {
                // On ouvre le contenu associé à l'URI
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(uriPathPicture));

                    // Rotation à 90 de l'image
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    imageView.setImageBitmap(rotatedBitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void startFragementFilters(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.filters_stickers_fragment_container, new FilterFragment()).commit();
    }

    public void startFragementStickers(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.filters_stickers_fragment_container, new StickerFragment()).commit();
    }

    public void stopPictureActivity(View view) {
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());

        ContentResolver resolver = getContentResolver();
        String uriPathCustomPicture = MediaStore.Images.Media.insertImage(resolver, bitmap, "picture"+new Date().hashCode(), "");

        Intent resultIntent = new Intent();
        resultIntent.putExtra("uri_path_custom_picture", uriPathCustomPicture);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }

    public void onClickNoFilter(View view) {
        imageView.getDrawable().clearColorFilter();
    }

    public void onClickFilter1(View view) {
        // Black and white filter
        float[] blackAndWhiteMatrix = {
                0.33f, 0.59f, 0.11f, 0, 0,  // Rouge
                0.33f, 0.59f, 0.11f, 0, 0,  // Vert
                0.33f, 0.59f, 0.11f, 0, 0,  // Bleu
                0,     0,     0,     1, 0   // Alpha
        };
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(blackAndWhiteMatrix);
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        imageView.getDrawable().setColorFilter(colorFilter);
    }

    public void onClickFilter2(View view) {
        // Negative filter
        float[] negativeMatrix = {
                -1, 0, 0, 0, 255, // Rouge
                0, -1, 0, 0, 255, // Vert
                0, 0, -1, 0, 255, // Bleu
                0, 0, 0, 1, 0     // Alpha
        };
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(negativeMatrix);
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        imageView.getDrawable().setColorFilter(colorFilter);
    }

    private final TabLayout.OnTabSelectedListener tabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()){

                // Filtres
                case 0:
                    startFragementFilters(fragmentContainerView);
                    return;

                // Stickers
                case 1:
                    startFragementStickers(fragmentContainerView);
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}

        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    };
}
