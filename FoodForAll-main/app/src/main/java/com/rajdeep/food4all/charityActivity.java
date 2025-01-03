package com.rajdeep.food4all;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class charityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NGOAdapter adapter;
    private List<NGO> ngoList;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),homeScreenActivity.class);
                startActivity(intent);
            }
        });



        recyclerView = findViewById(R.id.ngoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ngoList = new ArrayList<>();
        // Add sample data
        ngoList.add(new NGO("Care Foundation", "Mumbai", "123 Main Street"));
        ngoList.add(new NGO("Help International", "Delhi", "456 Park Avenue"));

        adapter = new NGOAdapter(this, ngoList);
        recyclerView.setAdapter(adapter);
    }
}