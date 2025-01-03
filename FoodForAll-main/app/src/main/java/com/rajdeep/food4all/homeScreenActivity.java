package com.rajdeep.food4all;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeScreenActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private Fragment activeFragment;
    private home homeFragment;
    private profileFragment profileFragment;
    private  donateFoodFormActivity donateFoodFormActivity;
    private ImpactFragment impactFragment;
    private  photosFragment photosFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);



        initializeFragments();
        setupBottomNavigation();


    }

    private void initializeFragments() {
        homeFragment = new home();
        profileFragment = new profileFragment();
        donateFoodFormActivity = new donateFoodFormActivity();
        impactFragment = new ImpactFragment();
        photosFragment = new photosFragment();

        // Set home fragment as default
        activeFragment = homeFragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .commit();
    }

    private void setupBottomNavigation() {
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;

            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = homeFragment;
            }
             else if (item.getItemId() == R.id.navigation_profile) {
                selectedFragment = profileFragment;
            } else if (item.getItemId() == R.id.navigation_donate){
                 selectedFragment = donateFoodFormActivity;
            } else if (item.getItemId() == R.id.navigation_impact) {
                selectedFragment = impactFragment;
            }
             else if (item.getItemId() == R.id.navigation_discover) {
                selectedFragment = photosFragment;
            }
             //else if (item.getItemId() == R.id.navigation_discover) {
//                selectedFragment = profileFragment;
             else {
                return false;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();

            activeFragment = selectedFragment;
            return true;
        });
    }
}
