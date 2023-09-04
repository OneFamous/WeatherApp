package com.fatihates.weatherapp.data.service

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(
        name = "hourly")
        val weatherData: WeatherDataDto
)
