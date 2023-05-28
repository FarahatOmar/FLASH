package com.example.thedelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    SearchView searchView;
    Switch deliveriesSwitch;
    List<DataClass> deliveryList;
    List<DataClass> filteredList;
    MyAdapter deliveryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);

        // Initialize the RecyclerView and its adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        deliveryList = new ArrayList<>();
        filteredList = new ArrayList<>();
        deliveryAdapter = new MyAdapter(deliveryList);
        recyclerView.setAdapter(deliveryAdapter);
        deliveriesSwitch = findViewById(R.id.deliveriesSwitch);

        // Initialize the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("new Packages");

        deliveriesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the visibility of the delivery items based on the toggle button's state
                recyclerView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        // Retrieve data from Firebase and update the RecyclerView
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                deliveryList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataClass delivery = snapshot.getValue(DataClass.class);
                    if (delivery != null) {
                        delivery.setKey(snapshot.getKey());
                        deliveryList.add(delivery);
                    }
                }
                deliveryAdapter.setDeliveryList(deliveryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchDelivery(newText);
        return true;
    }

    private void searchDelivery(String query) {
        filteredList.clear();
        for (DataClass delivery : deliveryList) {
            if (delivery.getKey() != null && delivery.getKey().toLowerCase().contains(query.toLowerCase())) {

                filteredList.add(delivery);
            }
        }
        deliveryAdapter.setDeliveryList(filteredList);
    }
}
