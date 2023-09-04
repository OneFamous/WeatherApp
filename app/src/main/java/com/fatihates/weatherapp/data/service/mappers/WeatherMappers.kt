package com.fatihates.weatherapp.data.service.mappers

import com.fatihates.weatherapp.WeatherTypes.WeatherData
import com.fatihates.weatherapp.WeatherTypes.WeatherInfo
import com.fatihates.weatherapp.WeatherTypes.WeatherType
import com.fatihates.weatherapp.data.service.WeatherDataDto
import com.fatihates.weatherapp.data.service.WeatherDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed() { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weathercodes[index]
        val pressure = pressures[index]
        val windSpeed = windSpeeds[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map{
            it.data
        }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo{
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find{
        val hour = if(now.minute < 30 ) now.hour else now.hour +1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}


// BURAYA HAFTALIK OLARAK DA GELECEK