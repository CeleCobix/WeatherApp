package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class WeatherRepository(
    private val weatherApi: WeatherApi
) {

    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        locationName: String
    ): Result<Weather> = withContext(Dispatchers.IO) {
        try {
            val response = weatherApi.getWeather(latitude, longitude)
            val weather = mapToWeather(response, locationName)
            Result.success(weather)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun mapToWeather(response: WeatherResponseDto, locationName: String): Weather {
        return Weather(
            location = locationName,
            currentTemp = response.current.temperature_2m,
            feelsLike = response.current.apparent_temperature,
            weatherCode = response.current.weather_code,
            weatherDescription = getWeatherDescription(response.current.weather_code),
            uvIndex = response.current.uv_index,
            humidity = response.current.relative_humidity_2m,
            windSpeed = response.current.wind_speed_10m,
            precipitation = response.current.precipitation,
            airQuality = mapAirQuality(response.current.european_aqi),
            hourlyForecast = mapHourlyForecast(response.hourly),
            dailyForecast = mapDailyForecast(response.daily)
        )
    }

    private fun mapAirQuality(aqi: Int): String {
        return when (aqi) {
            in 0..20 -> "Muy buena"
            in 21..40 -> "Buena"
            in 41..60 -> "Moderada"
            in 61..80 -> "Mala"
            else -> "Muy mala"
        }
    }

    private fun mapHourlyForecast(hourlyDto: HourlyWeatherDto): List<HourlyForecast> {
        val forecasts = mutableListOf<HourlyForecast>()

        for (i in 0 until minOf(24, hourlyDto.time.size)) {
            val timeStr = hourlyDto.time[i]
            val hour = timeStr.substring(11, 13).toIntOrNull() ?: continue

            forecasts.add(
                HourlyForecast(
                    time = formatHourTime(hour),
                    temperature = hourlyDto.temperature_2m[i],
                    weatherCode = hourlyDto.weather_code[i]
                )
            )
        }

        return forecasts
    }

    private fun mapDailyForecast(dailyDto: DailyWeatherDto): List<DailyForecast> {
        val forecasts = mutableListOf<DailyForecast>()
        val dayFormat = SimpleDateFormat("EEEE", Locale("es", "ES"))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        for (i in 0 until minOf(7, dailyDto.time.size)) {
            val date = dateFormat.parse(dailyDto.time[i])
            val dayName = when (i) {
                0 -> "Hoy"
                1 -> "Mañana"
                else -> date?.let { dayFormat.format(it).replaceFirstChar { char -> char.uppercase() } } ?: ""
            }

            forecasts.add(
                DailyForecast(
                    day = dayName,
                    weatherCode = dailyDto.weather_code[i],
                    maxTemp = dailyDto.temperature_2m_max[i],
                    minTemp = dailyDto.temperature_2m_min[i],
                    precipitationProbability = dailyDto.precipitation_probability_max.getOrNull(i) ?: 0
                )
            )
        }

        return forecasts
    }

    private fun formatHourTime(hour: Int): String {
        return when {
            hour == 0 -> "12 am"
            hour == 12 -> "12 pm"
            hour < 12 -> "$hour am"
            else -> "${hour - 12} pm"
        }
    }

    private fun getWeatherDescription(code: Int): String {
        return when (code) {
            0 -> "Despejado"
            1, 2, 3 -> "Parcialmente nublado"
            45, 48 -> "Niebla"
            51, 53, 55 -> "Llovizna"
            61, 63, 65 -> "Lluvia"
            71, 73, 75 -> "Nieve"
            77 -> "Granizo"
            80, 81, 82 -> "Chubascos"
            85, 86 -> "Nevadas"
            95 -> "Tormenta"
            96, 99 -> "Tormenta con granizo"
            else -> "Desconocido"
        }
    }
}