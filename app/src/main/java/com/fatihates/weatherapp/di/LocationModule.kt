package com.fatihates.weatherapp.di

import com.fatihates.weatherapp.data.service.location.DefaultLocationTracker
import com.fatihates.weatherapp.data.service.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class LocationModule {
    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}