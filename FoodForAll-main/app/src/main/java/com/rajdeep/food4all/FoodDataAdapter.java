package com.rajdeep.food4all;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajdeep.food4all.R;

import java.util.List;
import java.util.Random;

public class FoodDataAdapter extends RecyclerView.Adapter<FoodDataAdapter.ViewHolder> {

    private List<donationFoodForm2Activity.FoodData> foodDataList;
    private DatabaseReference databaseReference;  // Firebase database reference

    public FoodDataAdapter(List<donationFoodForm2Activity.FoodData> foodDataList) {
        this.foodDataList = foodDataList;
        // Initialize Firebase reference
        this.databaseReference = FirebaseDatabase.getInstance().getReference("FoodData");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        donationFoodForm2Activity.FoodData foodData = foodDataList.get(position);
        holder.foodType.setText(foodData.foodType);
        holder.quantity.setText(foodData.quantity);
        holder.address.setText(foodData.address);

        holder.acceptButton.setOnClickListener(v -> {
            // Generate a random 6-digit OTP
            int otp = new Random().nextInt(900000) + 100000;
            holder.acceptButton.setVisibility(View.GONE);
            holder.declineButton.setVisibility(View.GONE);
            holder.otpText.setText("Accepted\nOTP: " + otp);
            holder.otpText.setVisibility(View.VISIBLE);
        });

        holder.declineButton.setOnClickListener(v -> {
            // Get the unique key of the foodData item
            String key =  foodData.key;

            // Remove the data from Firebase using the key
            if (key != null) {
                databaseReference.child(key).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            // Successfully removed from Firebase
                            foodDataList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, foodDataList.size());
                        })
                        .addOnFailureListener(e -> {
                            // Handle the failure (e.g., show a Toast)
                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        return foodDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodType, quantity, address, otpText;
        Button acceptButton, declineButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodType = itemView.findViewById(R.id.typeText);
            quantity = itemView.findViewById(R.id.quantityText);
            address = itemView.findViewById(R.id.addressText);
            otpText = itemView.findViewById(R.id.otpText);
            acceptButton = itemView.findViewById(R.id.acceptButton);
            declineButton = itemView.findViewById(R.id.declineButton);
        }
    }
}
