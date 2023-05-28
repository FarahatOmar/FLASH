package com.example.thedelivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DataClass> deliveryList;
    private List<DataClass> filteredList;

    public MyAdapter(List<DataClass> deliveryList) {
        this.deliveryList = deliveryList;
        this.filteredList = new ArrayList<>(deliveryList);
    }

    public void setDeliveryList(List<DataClass> deliveryList) {
        this.deliveryList = deliveryList;
        this.filteredList = new ArrayList<>(deliveryList);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        filteredList.clear();

        if (query.isEmpty()) {
            filteredList.addAll(deliveryList);
        } else {
            query = query.toLowerCase();

            for (DataClass delivery : deliveryList) {
                if (delivery.getKey() != null && delivery.getKey().toLowerCase().contains(query.toLowerCase())) {

                    filteredList.add(delivery);
                }
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataClass delivery = filteredList.get(position);

        // Set the delivery data to the corresponding views in the item layout
        holder.nameTextView.setText("Sender: " + delivery.getDataName());
        holder.addressTextView.setText("Address: " + delivery.getDataAddress());
        holder.pickupTextView.setText("Pickup Address: " + delivery.getDataPickup());
        holder.packageTypeTextView.setText("Package Type: " + delivery.getPackageType());
        holder.statusTextView.setText("Status: " + delivery.getPackageStatus());
        holder.pickupTimeTextView.setText("PickUp Time: " + delivery.getEstimatedPickupTime());
        holder.deliveryTimeTextView.setText("Delivery Time: " + delivery.getEstimatedDeliveryTime());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        TextView pickupTextView;
        TextView packageTypeTextView;
        TextView statusTextView;
        TextView pickupTimeTextView;
        TextView deliveryTimeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            pickupTextView = itemView.findViewById(R.id.pickupTextView);
            packageTypeTextView = itemView.findViewById(R.id.packageTypeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            pickupTimeTextView = itemView.findViewById(R.id.pickupTimeTextView);
            deliveryTimeTextView = itemView.findViewById(R.id.deliveryTimeTextView);
        }
    }

}
