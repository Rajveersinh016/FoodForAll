package com.rajdeep.food4all;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class donateFoodFormActivity extends Fragment {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private View view;
    private Spinner spinner;
    private EditText quantityInput, typeInput, timeInput, addressInput;
    private Button cancelButton, submitButton, getLocationButton;
    private DatabaseReference databaseReference;

    public donateFoodFormActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_donate_food_form_activity, container, false);

        initializeViews();
        setupSpinner();
        setupLocationClient();

        timeInput.setOnClickListener(v -> datepicker());


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), homeScreenActivity.class);
                startActivity(intent);
            }
        });

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToFirebase();
            }
        });

        return view;
    }

    private void datepicker() {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                    timeInput.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }
            }, year, month, day);

            // Set the minimum date to today
            dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

            dialog.show();
        }







    private void initializeViews() {
        spinner = view.findViewById(R.id.foodTypeSpinner);
        getLocationButton = view.findViewById(R.id.getLocation);
        quantityInput = view.findViewById(R.id.quantityInput);
        typeInput = view.findViewById(R.id.typeInput);
        timeInput = view.findViewById(R.id.timeInput);
        addressInput = view.findViewById(R.id.addressInput);
        submitButton = view.findViewById(R.id.submitButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("FoodData");
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_item_layout,
                new String[]{"Prepared Food", "Packed Food", "Groceries"}
        );
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner.setAdapter(adapter);
    }

    private void setupLocationClient() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses != null && !addresses.isEmpty()) {
                                Address address = addresses.get(0);
                                addressInput.setText(address.getAddressLine(0));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveDataToFirebase() {
        String foodType = spinner.getSelectedItem().toString();
        String quantity = quantityInput.getText().toString().trim();
        String type = typeInput.getText().toString().trim();
        String time = timeInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();

        if (quantity.isEmpty() || type.isEmpty() || time.isEmpty() || address.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String entryId = databaseReference.push().getKey();
        FoodData foodData = new FoodData(foodType, quantity, type, time, address);

        if (entryId != null) {
            databaseReference.child(entryId).setValue(foodData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(requireContext(), "Failed to save data", Toast.LENGTH_SHORT).show();
                    }
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

        public FoodData() {
            // Default constructor required for Firebase
        }

        public FoodData(String foodType, String quantity, String type, String time, String address) {
            this.foodType = foodType;
            this.quantity = quantity;
            this.type = type;
            this.time = time;
            this.address = address;
        }
    }
}
