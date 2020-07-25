package com.example.loginapp.API

import com.example.loginapp.Model.Images
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("Photos")
    fun getUserDetails(): Call<MutableList<Images?>?>?
}