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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class donationFoodForm2Activity extends AppCompatActivity {

    Spinner spinner;

    EditText quantityInput,typeInput,timeInput,addressInput;

    Button cancelButton,submitButton;

    private DatabaseReference databaseReference;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donation_food_form2);

        spinner = findViewById(R.id.foodTypeSpinner);
        quantityInput = findViewById(R.id.quantityInput);
        typeInput = findViewById(R.id.typeInput);
        timeInput = findViewById(R.id.timeInput);
        addressInput = findViewById(R.id.addressInput);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
        back = findViewById(R.id.back);
        setupSpinner();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),homeScreenActivity.class);
                startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("FoodData");

        // Set listeners
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),homeScreenActivity.class);
            startActivity(intent);
            finish();
        });

        submitButton.setOnClickListener(view -> saveDataToFirebase());
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item_layout,
                new String[]{"Prepared Food", "Packed Food", "Groceries"}
        );
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner.setAdapter(adapter);
    }

    private void saveDataToFirebase() {
        String foodType = spinner.getSelectedItem().toString();
        String quantity = quantityInput.getText().toString().trim();
        String type = typeInput.getText().toString().trim();
        String time = timeInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();

        if (quantity.isEmpty() || type.isEmpty() || time.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String entryId = databaseReference.push().getKey();

        // Create a data object
        FoodData foodData = new FoodData(foodType, quantity, type, time, address,entryId);

        if (entryId != null) {
            databaseReference.child(entryId).setValue(foodData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void clearFields() {
        spinner.setSelection(0);
        quantityInput.setText("");
        typeInput.setText("");
        timeInput.setText("");
        addressInput.setText("");
    }

    public static class FoodData {
        public String foodType, quantity, type, time, address;
        public String key;

        public FoodData() {
            // Default constructor required for Firebase
        }

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
