package com.example.loginapp.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.loginapp.R

class Contact_Us : Fragment(), View.OnClickListener {
    private lateinit var email: TextView
    private lateinit var phone: TextView
    private lateinit var emailString: String
    private lateinit var phoneString: String

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_email_number, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initTypes()
        initListeners()
    }

    private fun initTypes() {
        email = view?.findViewById(R.id.email)!!
        phone = view?.findViewById(R.id.phone)!!
        emailString = email.text.toString()
        phoneString = phone.text.toString()
    }

    private fun initListeners() {
        email.setOnClickListener(this)
        phone.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.email -> {val intent: Intent = Intent(Intent.ACTION_SEND);
                intent.type = "text/plain";
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String?>(emailString))
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
                intent.putExtra(Intent.EXTRA_TEXT, "Body of message")
                startActivity(intent)
            }
            R.id.phone -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneString")
                startActivity(intent)
            }
        }
    }
}