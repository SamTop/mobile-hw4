package com.example.hw3.repository

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("error") val error: Error?,
    @SerializedName("current") val weather: WeatherInfo?,
)

data class Error(
    @SerializedName("message") val message: String,
)

data class WeatherInfo(
    @SerializedName("temp_c") val degrees: String?,
)
