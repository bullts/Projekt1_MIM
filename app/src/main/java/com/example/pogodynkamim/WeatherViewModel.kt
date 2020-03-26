package com.example.pogodynkamim

import CurrentWeather
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pogodynkamim.data.CurrentWeatherResponse


class WeatherViewModel(val context: Context,val city: String) : ViewModel() {
    private val weather = CurrentWeather(city)
    val allWeather: LiveData<CurrentWeatherResponse> = weather.list
}
