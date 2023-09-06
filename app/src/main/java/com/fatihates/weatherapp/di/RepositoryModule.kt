package com.fatihates.weatherapp.di

import com.fatihates.weatherapp.data.service.location.DefaultLocationTracker
import com.fatihates.weatherapp.data.service.location.LocationTracker
import com.fatihates.weatherapp.data.service.repository.WeatherRepositoryImpl
import com.fatihates.weatherapp.main.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}