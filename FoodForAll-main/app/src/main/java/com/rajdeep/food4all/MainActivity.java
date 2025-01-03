package com.rajdeep.food4all;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView creatAccounts;
    Button signInButton;
    EditText useremail,userpassword;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static final String SHARED_PREFS = "sharedprefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        checkBox();
        showPassword();
        useremail = findViewById(R.id.emailEditText);
        userpassword = findViewById(R.id.passwordEditText);
        creatAccounts = findViewById(R.id.creatAccount);
        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email,password;

                email = String.valueOf(useremail.getText());
                password = String.valueOf(userpassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (useremail.getText().toString().equals("val") && userpassword.getText().toString().equals("val")){
                    Intent intent = new Intent(getApplicationContext(),volunteerActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                if (useremail.getText().toString().equals("ngo") && userpassword.getText().toString().equals("ngo")){
                    Intent intent = new Intent(getApplicationContext(),NGOHomeScreen.class);
                    startActivity(intent);
                    finish();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){

                                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name","true");
                                    editor.apply();
                                    Toast.makeText(MainActivity.this, "Login Succesful!!!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),homeScreenActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else{
                                    Toast.makeText(MainActivity.this, "Wrong email/password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        creatAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),signup.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private void checkBox() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check = sharedPreferences.getString("name", "");
        if (check.equals("true")) {


            Intent intent = new Intent(getApplicationContext(),homeScreenActivity.class);
            startActivity(intent);
            finish();

        }
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