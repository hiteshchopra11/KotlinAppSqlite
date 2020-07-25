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
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class Login extends AppCompatActivity {
    EditText email, password;
    TextView login;
    String emailString, passwordString, emailSignUp, passwordSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();
        password = findViewById(R.id.password);
        login = findViewById(R.id.signInButton);
        password.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailString = email.getText().toString();
                passwordString = password.getText().toString();
                if (emailString.length() == 0 || passwordString.length() == 0) {
                    Toast.makeText(Login.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    setContentView(R.layout.loading_layout);
                    mAuth.signInWithEmailAndPassword(emailString, passwordString)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Login", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(Login.this, "Successfully signed in",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, HomeActivity.class);
                                        Bundle args = new Bundle();
                                        args.putSerializable("EMAIL", (Serializable) emailString);
                                        intent.putExtras(args);
                                        intent.putExtra("BUNDLE", args);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        setContentView(R.layout.activity_login);
                                        Log.w("Login", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });
    }
}