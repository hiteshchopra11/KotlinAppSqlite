package com.example.loginapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.PasswordAsterisk.AsteriskPasswordTransformationMethod;
import com.example.loginapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText email, name, password, confirmPassword;
    String emailString, emailPattern, nameText, passwordText, confirmPasswordText;
    TextView register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        register = findViewById(R.id.signUpButton);

        password.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        confirmPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
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
                    setContentView(R.layout.loading_layout);
                    mAuth.createUserWithEmailAndPassword(emailString, passwordText)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Register", "createUserWithEmail:success");
                                        Intent intent = new Intent(Register.this, Login.class);
                                        Toast.makeText(Register.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        setContentView(R.layout.activity_register);
                                        // If sign in fails, display a message to the user.
                                        Log.w("Register", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Register.this, "Registration failed as " + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }


    boolean checkIfFilled(String name, String email, String password, String confirmPassword) {
        if ((!name.isEmpty()) && (!email.isEmpty()) && (!password.isEmpty()) && (!confirmPassword.isEmpty()))
            return true;
        else
            Toast.makeText(Register.this, "Please don't leave anything empty", Toast.LENGTH_SHORT).show();
        return false;
    }

    boolean emailVerify(String email) {
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            return true;
        } else {
            Toast.makeText(Register.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean passwordMatch(String password, String confirmPassword) {
        if (password.equals(confirmPassword))
            return true;
        else
            Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        return false;
    }


    boolean minimumPasswordLength(String password) {
        if (password.length() < 6) {
            Toast.makeText(Register.this, "Minimum length of password should be 6", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
}