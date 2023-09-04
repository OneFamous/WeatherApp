package com.fatihates.weatherapp.WeatherTypes

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?

)
