package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText email, name, password, confirmPassword;
    String emailString, emailPattern, nameText, passwordText, confirmPasswordText;
    TextView register;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        register = findViewById(R.id.signUpButton);

        password.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        confirmPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        prefs = getSharedPreferences("your_file_name", MODE_PRIVATE);
        editor = prefs.edit();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailString = email.getText().toString().trim();
                nameText = name.getText().toString();
                passwordText = password.getText().toString();
                confirmPasswordText = confirmPassword.getText().toString();
                Log.e("DATA", "a" + nameText);
                Log.e("DATA", "a" + emailString);
                Log.e("DATA", "a" + passwordText);
                Log.e("DATA", "a" + confirmPasswordText);

                if ((checkIfFilled(nameText, emailString, passwordText, confirmPasswordText)) && emailVerify(emailString) && passwordMatch(passwordText, confirmPasswordText) && minimumPasswordLength(passwordText) && minimumPasswordLength(confirmPasswordText)) {
                    Toast.makeText(Register.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();
                    editor.putString("Email", emailString);
                    editor.putString("Password", passwordText);
                    editor.apply();
                    finish();
                } else
                    editor.putBoolean("Registered", false);
            }
        });
    }


    boolean checkIfFilled(String name, String email, String password, String confirmPassword) {
        if ((!name.isEmpty()) && (!email.isEmpty()) && (!password.isEmpty()) && (!confirmPassword.isEmpty()))
            return true;
        else
            Toast.makeText(this, "Please don't leave anything empty", Toast.LENGTH_SHORT).show();
        return false;
    }

    boolean emailVerify(String email) {
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            return true;
        } else {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean passwordMatch(String password, String confirmPassword) {
        if (password.equals(confirmPassword))
            return true;
        else
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        return false;
    }


    boolean minimumPasswordLength(String password) {
        if (password.length() < 6) {
            Toast.makeText(this, "Minimum length of password should be 6", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }


}