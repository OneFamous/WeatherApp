package com.fatihates.weatherapp

data class WeatherData(
    val location: List<String>,
    val weeklyForecast: List<WeatherForecast>,
    val dailyForecast: List<WeatherForecast>,
    val hourlyForecast: List<WeatherForecast>
)

data class WeatherForecast(
    val date: String,
    val description: String,
    val temperature: Double,
    )
