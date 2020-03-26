package com.example.pogodynkamim.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val APIKEY = "c4ffc65c1eda93d335ccc1b45fbab2cc"

interface ICurrentWeather {
    @GET("weather")
    fun getWeatherResponse(
        @Query("q") location: String,
        @Query("appid") key: String
    ) : Call<CurrentWeatherResponse>
}