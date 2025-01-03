package com.rajdeep.food4all;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class deliveryFragment extends Fragment {



    private View view;
    private RecyclerView recyclerView;
    private FoodDataAdapter adapter;
    private DatabaseReference databaseReference;
    private List<donationFoodForm2Activity.FoodData> foodDataList;
    private Switch availabilitySwitch;

    public deliveryFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        availabilitySwitch = view.findViewById(R.id.availabilitySwitch); // Add the ID of the Switch from XML
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        foodDataList = new ArrayList<>();
        adapter = new FoodDataAdapter(foodDataList);
        recyclerView.setAdapter(adapter);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("FoodData");

        // Set up Switch listener
        availabilitySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView
                fetchDataFromFirebase(); // Fetch data if not already loaded
            } else {
                recyclerView.setVisibility(View.GONE); // Hide RecyclerView
            }
        });

        // Set initial visibility of RecyclerView based on Switch state
        recyclerView.setVisibility(availabilitySwitch.isChecked() ? View.VISIBLE : View.GONE);

        return view;
    }

    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodDataList.clear(); // Clear old data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Get the unique key from the snapshot
                    String key = dataSnapshot.getKey();
                    // Create the FoodData object and set the key
                    donationFoodForm2Activity.FoodData foodData = dataSnapshot.getValue(donationFoodForm2Activity.FoodData.class);
                    if (foodData != null) {
                        foodData.key = key; // Set the key to the FoodData object
                        foodDataList.add(foodData); // Add to list
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter about data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static class FoodData {
        public String foodType, quantity, type, time, address;
        public String key; // Add a key field to store the unique key from Firebase

        // Default constructor required for Firebase
        public FoodData() {
        }

        // Constructor to initialize FoodData object
        public FoodData(String foodType, String quantity, String type, String time, String address,String key) {
            this.foodType = foodType;
            this.quantity = quantity;
            this.type = type;
            this.time = time;
            this.address = address;
            this.key = key;
        }
    }

}