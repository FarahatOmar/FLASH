package com.example.thedelivery;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    Button saveButton;
    EditText uploadName, uploadAddress, uploadPickup;
    RadioGroup RadioGroupPackageType;
    TextView UploadTrackingNumber;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);

        UploadTrackingNumber = findViewById(R.id.UploadTrackingNumber);
        RadioGroupPackageType = findViewById(R.id.RadioGroupPackageType);

        uploadAddress = findViewById(R.id.uploadAddress);
        uploadName = findViewById(R.id.uploadName);
        uploadPickup = findViewById(R.id.uploadPickup);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });
    }

    public void uploadData() {
        String Name = uploadName.getText().toString();
        String Address = uploadAddress.getText().toString();
        String Pickup = uploadPickup.getText().toString();
        String trackingNumber = generateTrackingNumber();
        String packageStatus = "Pending"; // Set initial status as "Pending"
        String estimatedPickupTime = generateEstimatedPickupTime();
        String estimatedDeliveryTime = generateEstimatedDeliveryTime(estimatedPickupTime);
        int selectedRadioButtonId = RadioGroupPackageType.getCheckedRadioButtonId();
        String packageType = "";


        switch (selectedRadioButtonId) {
            case R.id.radioButtonSmall:
                packageType = "Small";
                break;
            case R.id.radioButtonMedium:
                packageType = "Medium";
                break;
            case R.id.radioButtonLarge:
                packageType = "Large";
                break;
        }

        DataClass dataClass = new DataClass(Name, Address, Pickup, packageType, packageStatus, estimatedPickupTime, estimatedDeliveryTime);
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("new Packages").child(trackingNumber)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(e -> Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show());
    }

    private String generateTrackingNumber() {
        // Generate a UUID as the tracking number
        return UUID.randomUUID().toString();
    }
    private String generateEstimatedPickupTime() {
        // Generate the estimated pickup time based on your desired logic
        // You can use Calendar or any other time/date manipulation methods
        // Here's an example of generating an estimated pickup time as 2 hours from the current time
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        return DateFormat.getDateTimeInstance().format(calendar.getTime());
    }

    private String generateEstimatedDeliveryTime(String estimatedPickupTime) {
        // Generate the estimated delivery time based on the estimated pickup time
        // You can use Calendar or any other time/date manipulation methods
        // Here's an example of generating an estimated delivery time as 24 hours from the estimated pickup time
        Calendar calendar = Calendar.getInstance();
        try {
            Date pickupTime = DateFormat.getDateTimeInstance().parse(estimatedPickupTime);
            calendar.setTime(pickupTime);
            calendar.add(Calendar.HOUR_OF_DAY, 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DateFormat.getDateTimeInstance().format(calendar.getTime());
    }
}




