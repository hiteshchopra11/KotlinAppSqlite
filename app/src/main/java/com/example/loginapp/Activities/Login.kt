package com.example.loginapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.Helper.DatabaseHelper
import com.example.loginapp.R

class Login : AppCompatActivity(), View.OnClickListener {
    private val activity = this@Login
    private lateinit var textInputEditTextEmail: EditText
    private lateinit var textInputEditTextPassword: EditText
    private lateinit var loginButton: TextView
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        initListeners()
        initObjects()
    }


    private fun initViews() {
        textInputEditTextEmail = findViewById(R.id.email)
        textInputEditTextPassword = findViewById(R.id.password)
        loginButton = findViewById(R.id.signInButton)

    }

    private fun initListeners() {
        loginButton.setOnClickListener(this)
    }

    private fun initObjects() {
        databaseHelper = DatabaseHelper(activity)

    }

    override fun onClick(p0: View?) {
        verifyFromSQLite()
    }

    private fun verifyFromSQLite() {
        if (textInputEditTextEmail.toString().isNotEmpty() && textInputEditTextPassword.toString().isNotEmpty()) {
            if (databaseHelper!!.checkUser(textInputEditTextEmail!!.text.toString().trim { it <= ' ' }, textInputEditTextPassword!!.text.toString().trim { it <= ' ' })) {
                val accountsIntent = Intent(activity, HomeActivity::class.java)
                accountsIntent.putExtra("EMAIL", textInputEditTextEmail!!.text.toString().trim { it <= ' ' })
                startActivity(accountsIntent)
            } else {
                // Snack Bar to show success message that record is wrong
                Toast.makeText(this, "WRONG CREDENTIALS", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}