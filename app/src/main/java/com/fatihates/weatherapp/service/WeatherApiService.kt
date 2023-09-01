package com.fatihates.weatherapp.service
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    fun getWeatherData(
        @Query("q") location: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>

}