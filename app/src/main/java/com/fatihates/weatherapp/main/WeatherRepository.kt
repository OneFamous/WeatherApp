package com.fatihates.weatherapp.main

import com.bumptech.glide.load.engine.Resource
import com.fatihates.weatherapp.WeatherTypes.WeatherInfo
import com.fatihates.weatherapp.service.SorE

interface WeatherRepository {
    suspend fun  getWeatherData(lat: Double, long: Double): SorE<WeatherInfo>
}