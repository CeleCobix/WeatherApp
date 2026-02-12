package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,apparent_temperature,relative_humidity_2m,weather_code,wind_speed_10m,uv_index,precipitation,is_day,european_aqi",
        @Query("hourly") hourly: String = "temperature_2m,weather_code,is_day",
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max",
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecastDays: Int = 7
    ): WeatherResponseDto

    companion object {
        const val BASE_URL = "https://api.open-meteo.com/"
    }
}