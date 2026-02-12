package com.example.weatherapp.data.model
data class WeatherResponseDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentWeatherDto,
    val hourly: HourlyWeatherDto,
    val daily: DailyWeatherDto
)

data class CurrentWeatherDto(
    val time: String,
    val temperature_2m: Double,
    val apparent_temperature: Double,
    val relative_humidity_2m: Int,
    val weather_code: Int,
    val wind_speed_10m: Double,
    val uv_index: Double,
    val precipitation: Double,
    val is_day: Int,
    val european_aqi: Int
)

data class HourlyWeatherDto(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val weather_code: List<Int>,
    val is_day: List<Int>
)

data class DailyWeatherDto(
    val time: List<String>,
    val weather_code: List<Int>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val precipitation_probability_max: List<Int>
)

data class Weather(
    val location: String,
    val currentTemp: Double,
    val feelsLike: Double,
    val weatherCode: Int,
    val weatherDescription: String,
    val uvIndex: Double,
    val humidity: Int,
    val windSpeed: Double,
    val precipitation: Double,
    val airQuality: String,
    val hourlyForecast: List<HourlyForecast>,
    val dailyForecast: List<DailyForecast>
)

data class HourlyForecast(
    val time: String,
    val temperature: Double,
    val weatherCode: Int
)

data class DailyForecast(
    val day: String,
    val weatherCode: Int,
    val maxTemp: Double,
    val minTemp: Double,
    val precipitationProbability: Int
)