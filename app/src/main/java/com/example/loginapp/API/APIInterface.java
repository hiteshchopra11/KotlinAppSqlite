package com.example.loginapp.API;

import android.graphics.Region;

import com.example.loginapp.Model.Images;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("Photos")
    Call<List<Images>> getUserDetails();
}
