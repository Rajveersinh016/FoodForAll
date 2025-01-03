package com.rajdeep.food4all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rajdeep.food4all.NGO;
import com.rajdeep.food4all.R;

import java.util.List;

public class NGOAdapter extends RecyclerView.Adapter<NGOAdapter.NGOViewHolder> {
    private List<NGO> ngoList;
    private Context context;

    public NGOAdapter(Context context, List<NGO> ngoList) {
        this.context = context;
        this.ngoList = ngoList;
    }

    @Override
    public NGOViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new NGOViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NGOViewHolder holder, int position) {
        NGO ngo = ngoList.get(position);
        holder.tvNgoName.setText(ngo.getName());
        holder.tvLocation.setText(ngo.getLocation());
        holder.tvAddress.setText(ngo.getAddress());

        holder.btnDonate.setOnClickListener(v -> {
            // Handle donation click
        });
    }

    @Override
    public int getItemCount() {
        return ngoList.size();
    }

    class NGOViewHolder extends RecyclerView.ViewHolder {
        TextView tvNgoName, tvLocation, tvAddress;
        Button btnDonate;

        NGOViewHolder(View itemView) {
            super(itemView);
            tvNgoName = itemView.findViewById(R.id.tvNgoName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnDonate = itemView.findViewById(R.id.btnDonate);
        }
    }
}