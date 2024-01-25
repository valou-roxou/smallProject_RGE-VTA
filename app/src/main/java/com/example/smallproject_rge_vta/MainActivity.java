package com.example.smallproject_rge_vta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Restaurant> restaurants = getListData();
        this.recyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new CustomRecyclerViewAdapter(this, restaurants));

        // RecyclerView scroll vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private  List<Restaurant> getListData() {
        List<Restaurant> list = new ArrayList<Restaurant>();
        Restaurant res1 = new Restaurant("bipboup", 3, 50);
        Restaurant res2 = new Restaurant("truc", 1, 100);
        Restaurant res3 = new Restaurant("miam", 5, 2);

        list.add(res1);
        list.add(res2);
        list.add(res3);

        return list;
    }


}