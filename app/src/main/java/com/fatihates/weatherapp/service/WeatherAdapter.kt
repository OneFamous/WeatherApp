package com.fatihates.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fatihates.weatherapp.WeatherForecast

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var weatherList: List<WeatherForecast> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item_layout, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.bind(weather)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    fun submitList(list: List<WeatherForecast>) {
        weatherList = list
        notifyDataSetChanged()
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val temperatureTextView: TextView = itemView.findViewById(R.id.temperatureTextView)

        fun bind(weather: WeatherForecast) {
            dateTextView.text = "Tarih: ${weather.date}"
            descriptionTextView.text = "Açıklama: ${weather.description}"
            temperatureTextView.text = "Sıcaklık: ${weather.temperature}°C"
        }
    }
}


