package com.example.smallproject_rge_vta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.example.smallproject_rge_vta.fragments.FilterFragment;
import com.example.smallproject_rge_vta.fragments.StickerFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;

public class PictureActivity extends AppCompatActivity {

    private ImageView imageView;

    private FragmentContainerView fragmentContainerView;

    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        fragmentContainerView = findViewById(R.id.filters_stickers_fragment_container);

        TabLayout tabLayout = findViewById(R.id.picture_tab_layout);
        tabLayout.addOnTabSelectedListener(tabListener);
        tabLayout.getTabAt(0).select();

        imageView = findViewById(R.id.filter_picture_picture);

        Intent intent = getIntent();
        if(intent != null) {
            String uriPathPicture = intent.getStringExtra("uri_path_picture");
            // Si on a bien une uri
            if(uriPathPicture != null) {
                // On ouvre le contenu associé à l'URI
                try (InputStream inputStream = getContentResolver().openInputStream(Uri.parse(uriPathPicture))){
                    // Affichage de l'image si on a son contenu
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    // Rotation à 90 de l'image
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    imageView.setImageBitmap(rotatedBitmap);
                    drawable = imageView.getDrawable();
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
        finish();
    }

    public void onClickNoFilter(View view) {
        drawable.clearColorFilter();
        imageView.setImageDrawable(drawable);
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
        drawable.setColorFilter(colorFilter);
        imageView.setImageDrawable(drawable);
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
        drawable.setColorFilter(colorFilter);
        imageView.setImageDrawable(drawable);
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
