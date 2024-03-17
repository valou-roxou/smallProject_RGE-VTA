package com.example.smallproject_rge_vta;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smallproject_rge_vta.dto.Picture;

public class GetPictureActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_picture);

        FirestoreManager.getPictureById(data -> {
            Picture picture = (Picture) data;
            ImageView imageView = findViewById(R.id.getImageFirestore);
            byte[] compressedData = Base64.decode(picture.contentB64, Base64.URL_SAFE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(compressedData, 0, compressedData.length);
            imageView.setImageBitmap(bitmap);
        }, "4591893c-24e7-4910-b710-e022392a7fb1");
    }
}
