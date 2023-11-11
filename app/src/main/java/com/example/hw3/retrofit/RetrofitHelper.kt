package com.example.hw3.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit() = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}