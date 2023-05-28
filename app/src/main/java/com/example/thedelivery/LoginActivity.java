package com.example.thedelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    private Button userButton;
    private Button courierButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Retrieve the references to the UI elements
        userButton = findViewById(R.id.userButton);
        courierButton = findViewById(R.id.courierButton);

        // Set click listeners for the buttons
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle user button click
                navigateToUserFlow();
            }
        });
        courierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle courier button click
                navigateToCourierFlow();
            }
        });
    }



    private void navigateToUserFlow() {
        // Start the user-related UI flow
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Optional: Close the login activity so the user can't go back to it
    }
    private void navigateToCourierFlow() {
        // Start the courier-related UI flow
        Intent intent = new Intent(LoginActivity.this, CourierActivity.class);
        startActivity(intent);
        finish(); // Optional: Close the login activity so the courier can't go back to it
    }
}


