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
    private val _liveDataWeather = MutableLiveData<HashMap<String, WeatherResponse>>()
    val liveDataWeather: LiveData<HashMap<String, WeatherResponse>> = _liveDataWeather

    fun loadWeather(q: String, apiKey: String) {
        viewModelScope.launch {
            val res = repository.loadWeather(q, apiKey)
            var newMap = HashMap<String, WeatherResponse>()
            if (_liveDataWeather.value != null) {
                newMap = _liveDataWeather.value?.let { HashMap(it) }!!
            }

            newMap.set(q, res)
            _liveDataWeather.postValue(newMap)
        }
    }
}
