package com.example.pogodynkamim.data


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)