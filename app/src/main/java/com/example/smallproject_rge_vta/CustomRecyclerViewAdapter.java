package com.example.smallproject_rge_vta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private List<Restaurant> restaurants;
    private Context context;
    private LayoutInflater mLayoutInflater;


    public CustomRecyclerViewAdapter(Context context, List<Restaurant> datas) {
        this.context = context;
        this.restaurants = datas;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // On applique le layout à la cardView
        View recyclerViewItem = mLayoutInflater.inflate(R.layout.recyclerview_item_layout, parent, false);

        recyclerViewItem.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RestaurantActivity.class);
            v.getContext().startActivity(intent);
        });
        return new RestaurantViewHolder(recyclerViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = this.restaurants.get(position);

        holder.restaurantNameView.setText(restaurant.getName());
        holder.setRestaurantStarsBar(restaurant.getStars());
        holder.restaurantLocationView.setText(""+restaurant.getLocation());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}
