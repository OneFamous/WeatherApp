package com.fatihates.weatherapp.data.service.location

import android.health.connect.datatypes.ExerciseRoute

interface LocationTracker {
    suspend fun getCurrentLocation(): ExerciseRoute.Location?

}