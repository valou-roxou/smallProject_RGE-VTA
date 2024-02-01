package com.example.smallproject_rge_vta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_playground) {
            startActivity(new Intent(this, Playground.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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