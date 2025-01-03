package com.rajdeep.food4all;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileFragment extends Fragment {

    public static final String SHARED_PREFS = "sharedprefs";



    private Button logout_button,account_settings_button,donation_preferences_button,more_options_button;


    private View view;

    TextView profile_email;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    public profileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        account_settings_button = view.findViewById(R.id.account_settings_button);
        account_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireActivity(),accountSettingsActivity.class);
                startActivity(intent);

            }
        });

        donation_preferences_button = view.findViewById(R.id.donation_preferences_button);
        donation_preferences_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(),donationPreferencesButtonActivity.class);
                startActivity(intent);
            }
        });

        more_options_button = view.findViewById(R.id.more_options_button);
        more_options_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(),moreOptionActivity.class);
                startActivity(intent);
            }
        });

        logout_button = view.findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", "");
                editor.apply();

                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });


        mAuth = FirebaseAuth.getInstance();

        // Find the TextView
        profile_email = view.findViewById(R.id.profile_email);

        // Set up AuthStateListener
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    String userEmail = user.getEmail();
                    profile_email.setText(userEmail);
                } else {
                    // User is signed out
                    profile_email.setText("No user signed in");
                }
            }
        };


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Add the AuthStateListener
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Remove the AuthStateListener
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

}

