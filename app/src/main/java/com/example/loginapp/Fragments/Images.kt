package com.example.loginapp.Fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.loginapp.R


class Images : Fragment(), View.OnClickListener {
    private lateinit var buttonImage: Button
    private lateinit var image: ImageView
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            try {
                val bundle = data?.extras
                val bitmap = bundle?.getParcelable<Bitmap?>("data")
                image.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initViews() {
        buttonImage = view?.findViewById(R.id.buttonImage)!!
        image = view?.findViewById(R.id.imageView)!!
    }

    private fun initListeners() {
        buttonImage.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // ******** code for crop image
        i.putExtra("crop", "true")
        i.putExtra("aspectX", 100)
        i.putExtra("aspectY", 100)
        i.putExtra("outputX", 200)
        i.putExtra("outputY", 200)
        try {
            i.putExtra("return-data", true)
            startActivityForResult(
                    Intent.createChooser(i, "Select Picture"), 0)
        } catch (ex: ActivityNotFoundException) {
            ex.printStackTrace()
        }
    }
}