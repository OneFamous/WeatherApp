package com.fatihates.weatherapp

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatihates.weatherapp.service.WeatherApiClient
import com.fatihates.weatherapp.service.WeatherResponse
import com.fatihates.weatherapp.service.WeatherApiService
import com.google.android.gms.location.LocationServices
//import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.Manifest


class MainActivity : AppCompatActivity() {

    private lateinit var weatherApiService: WeatherApiService
    private lateinit var weeklyWeatherAdapter: WeatherAdapter
    private lateinit var dailyWeatherAdapter: WeatherAdapter
    private lateinit var hourlyWeatherAdapter: WeatherAdapter
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hava durumu servisi oluştur
        weatherApiService = WeatherApiClient.create()


        // Haftalık, günlük ve saatlik hava durumu göstergeleri için RecyclerView'ları yapılandır
        setupRecyclerViews()

        checkLocationPermission()
    }

    private fun setupRecyclerViews() {
        // Haftalık hava durumu RecyclerView
        weeklyWeatherAdapter = WeatherAdapter()
        findViewById<RecyclerView>(R.id.weeklyWeatherRecyclerView).layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        findViewById<RecyclerView>(R.id.weeklyWeatherRecyclerView).adapter = weeklyWeatherAdapter

        // Günlük hava durumu RecyclerView
        dailyWeatherAdapter = WeatherAdapter()
        findViewById<RecyclerView>(R.id.dailyWeatherRecyclerView).layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        findViewById<RecyclerView>(R.id.dailyWeatherRecyclerView).adapter = dailyWeatherAdapter

        // Saatlik hava durumu RecyclerView
        hourlyWeatherAdapter = WeatherAdapter()
        findViewById<RecyclerView>(R.id.hourlyWeatherRecyclerView).layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        findViewById<RecyclerView>(R.id.hourlyWeatherRecyclerView).adapter = hourlyWeatherAdapter
    }
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Konum izni yoksa, izin iste
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Konum izni varsa, mevcut konumu al
            requestCurrentLocation()
        }
    }
    private fun requestCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                // Mevcut konumu alınca hava durumu verisini al
                val latitude = location.latitude
                val longitude = location.longitude
                val apiKey = "da37e8f99fef5becb04f1b648bb88929"
                getWeatherData(latitude, longitude, apiKey)
            } ?: run {
                Toast.makeText(
                    this,
                    "Konum bilgisi alınamadı",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getWeatherData(latitude: Double, longitude: Double, apiKey: String) {
        val call = weatherApiService.getWeatherData("$latitude,$longitude", apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()

                    // Hava durumu verilerini alarak görsel öğeleri güncelle
                    if (weatherResponse != null) {
                        if (weatherResponse.weatherList != null && !weatherResponse.weatherList.isEmpty()) {
                            val weatherData = weatherResponse.weatherList[0]

                            // Konum bilgisini güncelle
                            findViewById<TextView>(R.id.locationTextView).text =
                                "Konum: ${weatherData.location.joinToString(", ")}"

                            // Sıcaklık bilgisini güncelle
                            if (weatherData.hourlyForecast != null && !weatherData.hourlyForecast.isEmpty()) {
                                val temperature = weatherData.hourlyForecast[0].temperature
                                findViewById<TextView>(R.id.temperatureTextView).text =
                                    "Sıcaklık: ${temperature}°C"
                            } else {
                                findViewById<TextView>(R.id.temperatureTextView).text =
                                    "Sıcaklık bilgisi yok"
                            }

                            // Diğer hava durumu verilerini RecyclerView'larda göstermek için adapter'lara güncelleme yapabilirsiniz
                            weeklyWeatherAdapter.submitList(weatherData.weeklyForecast)
                            dailyWeatherAdapter.submitList(weatherData.dailyForecast)
                            hourlyWeatherAdapter.submitList(weatherData.hourlyForecast)
                        } else {
                            findViewById<TextView>(R.id.locationTextView).text =
                                "Konum bilgisi yok"
                        }
                    } else {
                        print("VERİLER ALINAMADI")
                    }
                } else {
                    print("API İSTEĞİ BAŞARISIZ")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Hata durumunda kullanıcıya hata mesajı gösterilebilir
                print("HATA: ${t.message}")
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Konum izni verildi, mevcut konumu al
                requestCurrentLocation()
            } else {
                // Kullanıcı izin vermedi, hata mesajı göster
                Toast.makeText(
                    this,
                    "Konum izni verilmedi, uygulamayı kullanabilmek için konum izni gereklidir.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}