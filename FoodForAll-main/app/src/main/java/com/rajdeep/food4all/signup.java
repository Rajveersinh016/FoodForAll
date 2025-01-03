package com.rajdeep.food4all;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    TextView logInAccount;
    Button signUpButton;
    EditText emailEditText,passwordEditText,mobileEditText,aadharCardNumberEditText,otpNumberEditText;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        showPassword();
        otpNumberEditText = findViewById(R.id.otpNumberEditText);
        aadharCardNumberEditText = findViewById(R.id.aadharCardNumberEditText);
        signUpButton = findViewById(R.id.signUpButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        logInAccount = findViewById(R.id.logInAccount);
        logInAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;
                email = String.valueOf(emailEditText.getText());
                password = String.valueOf(passwordEditText.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(signup.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(signup.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(mobileEditText.getText().toString())) {
                    Toast.makeText(signup.this, "Please enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(aadharCardNumberEditText.getText().toString())) {
                    Toast.makeText(signup.this, "Please enter aadharcard Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(otpNumberEditText.getText().toString())) {
                    Toast.makeText(signup.this, "Please enter OTP Number", Toast.LENGTH_SHORT).show();
                    return;
                }


                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(signup.this, "Account Created!!!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(signup.this, "Enter valid details!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
        private void showPassword() {
            EditText passwordEditText = findViewById(R.id.passwordEditText);
            ImageView togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility);
            togglePasswordVisibility.setOnClickListener(view -> {
                if (passwordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    // Hide password
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    togglePasswordVisibility.setImageResource(R.drawable.crosseye);
                } else {
                    // Show password
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    togglePasswordVisibility.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
                // Move the cursor to the end of the text
                passwordEditText.setSelection(passwordEditText.getText().length());
            });

        }

}