package com.example.pogodynkamim

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeatherViewModelFactory( val context: Context,val city: String): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>):T {
        return WeatherViewModel(context,city) as T
    }
}