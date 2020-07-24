package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class Login extends AppCompatActivity {
    EditText email, password;
    TextView login;
    Boolean Registered;
    SharedPreferences prefs;
    String emailString, passwordString, emailSignUp, passwordSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.signInButton);
        password.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        prefs = getSharedPreferences("your_file_name", MODE_PRIVATE);
        emailSignUp = prefs.getString("Email", null);
        passwordSignUp = prefs.getString("Password", null);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailString = email.getText().toString();
                passwordString = password.getText().toString();
                if (emailString.length() == 0 || passwordString.length() == 0) {
                    Toast.makeText(Login.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else if (emailString.matches("") && passwordString.matches("")) {
                    Toast.makeText(getApplicationContext(),
                            "Details does not belongs to any account",
                            Toast.LENGTH_SHORT).show();
                } else if (!(emailString.matches(emailSignUp))) {
                    Toast.makeText(getApplicationContext(),
                            "Either login/password is incorrect", Toast.LENGTH_SHORT)
                            .show();
                } else if (!(passwordString.matches(passwordSignUp))) {
                    Toast.makeText(getApplicationContext(),
                            "Either login/password is incorrect", Toast.LENGTH_SHORT)
                            .show();
                } else if ((emailString.matches(emailSignUp))
                        && (passwordString.matches(passwordSignUp))) {

                    prefs.edit().putString("loginTest", "true");
                    prefs.edit().apply();
                    Intent intent = new Intent(Login.this, HomeActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("EMAIL", (Serializable) emailString);
                    intent.putExtras(args);
                    intent.putExtra("BUNDLE", args);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),
                            "You have successfully login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}