package com.example.thedelivery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CourierActivity extends AppCompatActivity {

    private LinearLayout packageContainer;
    private DatabaseReference databaseReference;
    private static final String PREF_PICKED_UP_PACKAGES = "picked_up_packages";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        // Initialize views
        packageContainer = findViewById(R.id.packageContainer);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("new Packages");

        // Retrieve packages from Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot packageSnapshot : dataSnapshot.getChildren()) {
                    DataClass packageData = packageSnapshot.getValue(DataClass.class);
                    if (packageData != null) {
                        // Create a card for each package
                        createPackageCard(packageData, packageSnapshot.getKey());
                        if ("Delivered".equals(packageData.getPackageStatus())) {
                            CardView cardView = (CardView) packageContainer.getChildAt(packageContainer.getChildCount() - 1);
                            LinearLayout cardContent = (LinearLayout) cardView.getChildAt(0);
                            for (int i = 0; i < cardContent.getChildCount(); i++) {
                                View child = cardContent.getChildAt(i);
                                if (child instanceof TextView) {
                                    TextView textView = (TextView) child;
                                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

    private void createPackageCard(DataClass packageData, String packageKey) {
        // Create a new CardView

        final CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = getResources().getDimensionPixelSize(R.dimen.card_margin);
        layoutParams.setMargins(margin, margin, margin, margin);
        cardView.setLayoutParams(layoutParams);
        cardView.setCardElevation(getResources().getDimensionPixelSize(R.dimen.card_elevation));
        cardView.setRadius(getResources().getDimensionPixelSize(R.dimen.card_corner_radius));

        // Create a LinearLayout for card content
        LinearLayout cardContent = new LinearLayout(this);
        cardContent.setOrientation(LinearLayout.VERTICAL);
        cardContent.setPadding(
                getResources().getDimensionPixelSize(R.dimen.card_content_padding),
                getResources().getDimensionPixelSize(R.dimen.card_content_padding),
                getResources().getDimensionPixelSize(R.dimen.card_content_padding),
                getResources().getDimensionPixelSize(R.dimen.card_content_padding)
        );

        // Add package details to the card
        TextView packageNameTextView = new TextView(this);
        packageNameTextView.setText("Package Name: " + packageData.getDataName());

        TextView packageAddressTextView = new TextView(this);
        packageAddressTextView.setText("Package Address: " + packageData.getDataAddress());

        cardContent.addView(packageNameTextView);
        cardContent.addView(packageAddressTextView);

        // Create buttons for pickup and delivery
        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button pickupButton = new Button(this);
        pickupButton.setText("Picked Up");

        if (isPackagePickedUp(packageKey)) {
            pickupButton.setVisibility(View.GONE); // Hide the "Picked Up" button if already picked up
        } else {
            pickupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform pickup operation
                    performPickup(packageKey);
                    pickupButton.setVisibility(View.GONE); // Hide the "Picked Up" button after picking up
                    savePickedUpPackage(packageKey); // Save the picked-up package key in shared preferences
                }
            });
        }

        Button deliverButton = new Button(this);
        deliverButton.setText("Delivered");

        deliverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform delivery operation
                performDelivery(packageKey,cardView);
            }
        });



        buttonLayout.addView(pickupButton);
        buttonLayout.addView(deliverButton);

        cardContent.addView(buttonLayout);

        // Add card content to the CardView
        cardView.addView(cardContent);

        // Add the CardView to the package container
        packageContainer.addView(cardView);
    }

    private void performPickup(String packageKey) {
        // Update package status and perform necessary actions
        databaseReference.child(packageKey).child("packageStatus").setValue("Picked up")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CourierActivity.this, "Package picked up", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(CourierActivity.this, "Failed to pick up package", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void savePickedUpPackage(String packageKey) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_PICKED_UP_PACKAGES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(packageKey, true);
        editor.apply();
    }

    // Check if a package has been picked up based on the package key
    private boolean isPackagePickedUp(String packageKey) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_PICKED_UP_PACKAGES, MODE_PRIVATE);
        return sharedPreferences.getBoolean(packageKey, false);
    }

    private void performDelivery(String packageKey, CardView cardView) {
        // Update package status and perform necessary actions
        databaseReference.child(packageKey).child("packageStatus").setValue("Delivered")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CourierActivity.this, "Package delivered", Toast.LENGTH_SHORT).show();

                            // Apply strikethrough effect to the card content
                            LinearLayout cardContent = (LinearLayout) cardView.getChildAt(0);
                            for (int i = 0; i < cardContent.getChildCount(); i++) {
                                View child = cardContent.getChildAt(i);
                                if (child instanceof TextView) {
                                    TextView textView = (TextView) child;
                                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                }
                            }
                        } else {
                            Toast.makeText(CourierActivity.this, "Failed to deliver package", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
