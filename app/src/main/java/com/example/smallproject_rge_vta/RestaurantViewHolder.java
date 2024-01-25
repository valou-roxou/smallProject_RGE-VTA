package com.example.smallproject_rge_vta;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {

    public ImageView pictureView;
    public TextView restaurantNameView;
    public TextView restaurantLocationView;
    private TextView restaurantStarsView;
    private RatingBar restaurantStarsBar;

    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);

        this.pictureView = (ImageView) itemView.findViewById(R.id.picture_view);
        this.restaurantNameView = (TextView) itemView.findViewById(R.id.restaurant_name_view);
        this.restaurantLocationView = (TextView) itemView.findViewById(R.id.restaurant_location_view);
        this.restaurantStarsView = (TextView) itemView.findViewById(R.id.restaurant_stars_view);
        this.restaurantStarsBar = (RatingBar) itemView.findViewById(R.id.restaurant_stars_bar);
    }

    public void setRestaurantStarsBar(float stars) {
        this.restaurantStarsBar.setRating(stars);
        this.restaurantStarsView.setText(""+stars);
    }
}