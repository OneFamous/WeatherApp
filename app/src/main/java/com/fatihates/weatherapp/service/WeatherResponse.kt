package com.fatihates.weatherapp.service

import com.fatihates.weatherapp.WeatherData
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("list") val weatherList: List<WeatherData>
)
