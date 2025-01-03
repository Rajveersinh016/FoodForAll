package com.rajdeep.food4all;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razorpay.Checkout;

import org.json.JSONObject;

public class donateFundsActivity extends AppCompatActivity {

    ImageView back;
    EditText emailIdtxt,amountInput;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    Button btnRazorpay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donate_funds);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),homeScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        // Find the TextView
        emailIdtxt = findViewById(R.id.emailIdtxt);
        amountInput = findViewById(R.id.amountInput);

        // Set up AuthStateListener
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    String userEmail = user.getEmail();
                    emailIdtxt.setText(userEmail);
                } else {
                    // User is signed out
                    emailIdtxt.setText("No user signed in");
                }
            }
        };
        btnRazorpay = findViewById(R.id.btnRazorpay);
        btnRazorpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(emailIdtxt.getText().toString())){
                    emailIdtxt.setError("Requried");
                }
                if(TextUtils.isEmpty(amountInput.getText().toString())){
                    amountInput.setError("Requried");
                } else {
                    payment();
                }
            }
        });

    }

    private void payment() {

        Checkout checkout = new Checkout();

        checkout.setKeyID("rzp_live_PnnV5JWqKyp6bY");

        checkout.setImage(R.drawable.icon_events_prev_ui);

        final Activity activity = this;

        try {

            Integer amt = Integer.valueOf(amountInput.getText().toString());
            JSONObject option = new JSONObject();

            //option.put("name",sName.getText().toString());
            option.put("description",getIntent().getStringExtra("Name"));
            option.put("image",R.drawable.icon_events_prev_ui);
            option.put("theme.color","#3399cc");
            option.put("currency","INR");
            option.put("payment_capture",true);
            option.put("amount",100 * amt);
            option.put("prefill.email",emailIdtxt.getText().toString());
            //option.put("prefill.contact",sNumber.getText().toString());
            checkout.open(activity,option);

        } catch (Exception e){
            Log.e("TAG","Error", e);
        }
    }

    protected void onStart() {
        super.onStart();
        // Add the AuthStateListener
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Remove the AuthStateListener
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }
}