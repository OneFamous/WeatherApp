package com.fatihates.weatherapp.service

sealed class SorE<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : SorE<T>(data)
    class Error<T>(message: String, data: T? = null) : SorE<T>(data, message)
}