package com.rajdeep.food4all;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {

    private List<donateFoodFormActivity.FoodData> foodDataList;

    public DonationAdapter(List<donateFoodFormActivity.FoodData> foodDataList) {
        this.foodDataList = foodDataList;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_itemcard_design, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        donateFoodFormActivity.FoodData foodData = foodDataList.get(position);

        // Bind data to views
        holder.foodType.setText(foodData.foodType);
        holder.quantity.setText(foodData.quantity);
        holder.time.setText("Time: " + foodData.time);
        holder.location.setText(foodData.address);
        holder.btn.setOnClickListener(v -> {
            // Generate a random 6-digit OTP
            int otp = new Random().nextInt(900000) + 100000;
            holder.btn.setVisibility(View.GONE);
            holder.otpText.setText("Accepted\nOTP: " + otp);
            holder.otpText.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public int getItemCount() {
        return foodDataList.size();
    }

    static class DonationViewHolder extends RecyclerView.ViewHolder {
        TextView foodType, quantity, time, location,otpText,stutas;
        Button btn;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ensure all views are properly initialized
            foodType = itemView.findViewById(R.id.foodtype);
            quantity = itemView.findViewById(R.id.quantity);
            time = itemView.findViewById(R.id.time);
            location = itemView.findViewById(R.id.location);
            btn = itemView.findViewById(R.id.acceptButton);
            otpText = itemView.findViewById(R.id.otpText);
            // Add in layout if required
        }
    }
}
