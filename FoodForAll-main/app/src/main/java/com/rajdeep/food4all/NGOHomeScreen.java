package com.rajdeep.food4all;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NGOHomeScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DonationAdapter donationAdapter;
    private List<donateFoodFormActivity.FoodData> foodDataList = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngohome_screen); // Use the correct layout file name here

        initializeViews();
        setupRecyclerView();
        fetchDataFromFirebase();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        donationAdapter = new DonationAdapter(foodDataList);
        recyclerView.setAdapter(donationAdapter);
    }

    private void fetchDataFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("FoodData");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodDataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    donateFoodFormActivity.FoodData foodData = dataSnapshot.getValue(donateFoodFormActivity.FoodData.class);
                    if (foodData != null) {
                        foodDataList.add(foodData);
                    }
                }
                donationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NGOHomeScreen.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
