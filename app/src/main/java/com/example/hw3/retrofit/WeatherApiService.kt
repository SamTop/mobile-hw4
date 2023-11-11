package com.example.hw3.retrofit

import com.example.hw3.repository.WeatherResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/current.json")
    suspend fun fetchWeather(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): WeatherResponse
}
