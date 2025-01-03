package com.rajdeep.food4all;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class donationCharityFoodForm extends AppCompatActivity {

    private Spinner spinner;
    private ImageView back;
    private EditText etName, etEmail, etMobileNumber, etOrganizationName, etItemQuantity;
    private Button btnSubmitDonation;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_charity_food_form);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Donations");

        // Initialize UI elements
        spinner = findViewById(R.id.foodTypeSpinner);
        back = findViewById(R.id.back);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etOrganizationName = findViewById(R.id.etOrganizationName);
        etItemQuantity = findViewById(R.id.etItemQuantity);
        btnSubmitDonation = findViewById(R.id.btnSubmitDonation);

        setupSpinner();

        // Back button functionality
        back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), homeScreenActivity.class);
            startActivity(intent);
        });

        // Submit button functionality
        btnSubmitDonation.setOnClickListener(view -> saveDataToFirebase());
    }

    private void setupSpinner() {
        // Spinner setup with predefined food types
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item, // Use a system-provided layout for simplicity
                new String[]{"Clothing", "Books", "Electronics", "Furniture", "Medical supplies", "Other"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void saveDataToFirebase() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String mobileNumber = etMobileNumber.getText().toString().trim();
        String organizationName = etOrganizationName.getText().toString().trim();
        String itemQuantity = etItemQuantity.getText().toString().trim();
        String foodType = spinner.getSelectedItem().toString();

        if (name.isEmpty() || email.isEmpty() || mobileNumber.isEmpty() || organizationName.isEmpty() || itemQuantity.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String donationId = databaseReference.push().getKey();
        DonationData donationData = new DonationData(name, email, mobileNumber, organizationName, itemQuantity, foodType);

        if (donationId != null) {
            databaseReference.child(donationId).setValue(donationData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Donation submitted successfully", Toast.LENGTH_SHORT).show();
                            clearFields();
                        } else {
                            Toast.makeText(this, "Failed to submit donation", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void clearFields() {
        etName.setText("");
        etEmail.setText("");
        etMobileNumber.setText("");
        etOrganizationName.setText("");
        etItemQuantity.setText("");
        spinner.setSelection(0);
    }

    public static class DonationData {
        public String name, email, mobileNumber, organizationName, itemQuantity, foodType;

        public DonationData() {
            // Default constructor required for Firebase
        }

        public DonationData(String name, String email, String mobileNumber, String organizationName, String itemQuantity, String foodType) {
            this.name = name;
            this.email = email;
            this.mobileNumber = mobileNumber;
            this.organizationName = organizationName;
            this.itemQuantity = itemQuantity;
            this.foodType = foodType;
        }
    }
}
