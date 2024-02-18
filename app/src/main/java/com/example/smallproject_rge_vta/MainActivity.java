package com.example.smallproject_rge_vta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.snackbar.BaseTransientBottomBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();

        List<Restaurant> restaurants = getListData();
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new CustomRecyclerViewAdapter(this, restaurants));

        // RecyclerView scroll vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(bundle != null) {
            String popUpSuccess = bundle.getString("pop_up_success");
            if(popUpSuccess != null) {
                CustomSnackbar.make(findViewById(android.R.id.content), popUpSuccess, BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }
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
            startActivity(new Intent(this, PlaygroundActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private  List<Restaurant> getListData() {
        List<Restaurant> list = new ArrayList<Restaurant>();
        Restaurant res1 = new Restaurant("bipboup", 3, 50);
        Restaurant res2 = new Restaurant("truc", 1, 100);
        Restaurant res3 = new Restaurant("miam", 2.3f, 9);
        Restaurant res4 = new Restaurant("prout", 5.9f, 2);
        Restaurant res5 = new Restaurant("ouf", 2.7f, 222);
        Restaurant res6 = new Restaurant("Grosse Patate", 5, 4);

        list.add(res1);
        list.add(res2);
        list.add(res3);
        list.add(res4);
        list.add(res5);
        list.add(res6);

        return list;
    }


}