package com.fatihates.weatherapp.data.service.repository


import com.bumptech.glide.load.engine.Resource
import com.fatihates.weatherapp.WeatherTypes.WeatherInfo
import com.fatihates.weatherapp.data.service.WeatherApi
import com.fatihates.weatherapp.data.service.mappers.toWeatherInfo
import com.fatihates.weatherapp.main.WeatherRepository
import com.fatihates.weatherapp.service.SorE
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): SorE<WeatherInfo> {
        return try {
            SorE.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )

        } catch (e: Exception) {
            e.printStackTrace()
            SorE.Error(e.message ?: "An unknown error occurred.")
        }
    }
}