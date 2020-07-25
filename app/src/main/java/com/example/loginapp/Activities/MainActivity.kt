package com.example.loginapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var signIn: TextView
    private lateinit var signUp: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initListeners()
    }

    private fun initViews() {
        signIn = findViewById(R.id.signIn)
        signUp = findViewById(R.id.signUp)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.signUp -> startActivity(Intent(this@MainActivity, Register::class.java))
            R.id.signIn -> startActivity(Intent(this@MainActivity, Login::class.java))
        }
    }

    private fun initListeners() {
        signIn.setOnClickListener(this)
        signUp.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        return
    }
}

