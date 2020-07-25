package com.example.loginapp.Activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.Helper.DatabaseHelper
import com.example.loginapp.Model.User
import com.example.loginapp.R

class Register : AppCompatActivity(), View.OnClickListener {
    private val regex: Regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    private val activity = this@Register
    private lateinit var textInputEditTextName: EditText
    private lateinit var textInputEditTextEmail: EditText
    private lateinit var textInputEditTextPassword: EditText
    private lateinit var textInputEditTextConfirmPassword: EditText
    private lateinit var buttonRegister: TextView
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        // initializing the views
        initViews()
        // initializing the listeners
        initListeners()
        // initializing the objects
        initObjects()
    }

    private fun initViews() {
        textInputEditTextName = findViewById(R.id.name)
        textInputEditTextEmail = findViewById(R.id.email)
        textInputEditTextPassword = findViewById(R.id.password)
        textInputEditTextConfirmPassword = findViewById(R.id.confirm_password)
        buttonRegister = findViewById(R.id.signUpButton)
    }

    private fun initListeners() {
        buttonRegister.setOnClickListener(this)
    }

    private fun initObjects() {
        databaseHelper = DatabaseHelper(activity)
    }

    override fun onClick(p0: View?) {
        postDataToSQLite()
    }

    private fun postDataToSQLite() {
        if (checkIfFilled(textInputEditTextName.text.toString(), textInputEditTextEmail.text.toString(), textInputEditTextPassword.text.toString(), textInputEditTextConfirmPassword.text.toString()) && emailVerify(textInputEditTextEmail.text.toString()) && passwordMatch(textInputEditTextPassword.text.toString(), textInputEditTextConfirmPassword.text.toString()) && minimumPasswordLength(textInputEditTextPassword.text.toString()) && minimumPasswordLength(textInputEditTextConfirmPassword.text.toString())) {
            if (!databaseHelper.checkUser(textInputEditTextEmail.text.toString().trim())) {
                val user = User(name = textInputEditTextName.text.toString().trim(),
                        email = textInputEditTextEmail.text.toString().trim(),
                        password = textInputEditTextPassword.text.toString().trim())
                databaseHelper.addUser(user)
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Already Registered", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun checkIfFilled(name: String, email: String, password: String, confirmPassword: String): Boolean {
        return if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            true
        } else {
            Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun emailVerify(email: String): Boolean {
        return if (email.matches(regex)) true
        else {
            Toast.makeText(activity, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun passwordMatch(password: String, confirmPassword: String): Boolean {
        return if (password == confirmPassword) true
        else {
            Toast.makeText(activity, "Passwords do not match", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun minimumPasswordLength(password: String): Boolean {
        return if (password.length > 5) true
        else {
            Toast.makeText(activity, "Please enter at least 6 digits", Toast.LENGTH_SHORT).show()
            false
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}
