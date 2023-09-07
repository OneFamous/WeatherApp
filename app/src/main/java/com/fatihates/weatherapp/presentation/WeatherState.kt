package com.fatihates.weatherapp.presentation

import com.fatihates.weatherapp.WeatherTypes.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null


)
