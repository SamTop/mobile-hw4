package com.example.hw3.repository

import com.example.hw3.retrofit.WeatherApiService
import com.example.hw3.retrofit.RetrofitHelper

class DataLoaderRepository {
    suspend fun loadWeather(q: String, apiKey: String): WeatherResponse {
        val apiService = RetrofitHelper.getRetrofit().create(WeatherApiService::class.java)
        return apiService.fetchWeather(
            q,
            apiKey,
        )
    }


}
