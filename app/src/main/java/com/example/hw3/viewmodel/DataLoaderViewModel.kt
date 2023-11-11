package com.example.hw3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.hw3.repository.DataLoaderRepository
import com.example.hw3.repository.WeatherResponse

class DataLoaderViewModel : ViewModel() {
    private val repository = DataLoaderRepository()
    private val _liveDataWeather = MutableLiveData<WeatherResponse>()
    val liveDataWeather: LiveData<WeatherResponse> = _liveDataWeather

    fun loadWeather(q: String, apiKey: String) {
        viewModelScope.launch {
            _liveDataWeather.postValue(repository.loadWeather(q, apiKey))
        }
    }
}
