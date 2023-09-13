package com.fatihates.weatherapp.data.service.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?

}