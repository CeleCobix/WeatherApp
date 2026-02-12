package com.example.weatherapp.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.weatherapp.R

object WeatherCodeMapper {

    fun getDescription(code: Int): String {
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

    fun getIconResource(code: Int, isDay: Boolean = true): Int {
        return when (code) {
            0 -> if (isDay) R.drawable.ic_clear_day else R.drawable.ic_clear_night
            1, 2 -> if (isDay) R.drawable.ic_partly_cloudy_day else R.drawable.ic_partly_cloudy_night
            3 -> R.drawable.ic_cloudy
            45, 48 -> if (isDay) R.drawable.ic_fog_day else R.drawable.ic_fog_night
            51, 53, 55 -> R.drawable.ic_drizzle_cloudy
            61, 63 -> if (isDay) R.drawable.ic_partly_cloudy_day_rain else R.drawable.ic_partly_cloudy_night_rain
            65 -> R.drawable.ic_rain_cloudy
            71, 73, 75 -> R.drawable.ic_snow_cloudy
            77 -> R.drawable.ic_hail_cloudy
            80, 81, 82 -> R.drawable.ic_rain_cloudy
            85, 86 -> R.drawable.ic_snow_cloudy
            95 -> R.drawable.ic_thunderstorms_cloudy
            96, 99 -> R.drawable.ic_thunderstorms_rain_cloudy
            else -> if (isDay) R.drawable.ic_clear_day else R.drawable.ic_clear_night
        }
    }

    fun mapUvIndexToMessage(uvIndex: Int): String {
        return when (uvIndex) {
            in 0..2 -> "Bajo"
            in 3..5 -> "Moderado"
            in 6..7 -> "Alto"
            in 8..10 -> "Muy alto"
            else -> "Extremo"
        }
    }

    fun mapHumidityToMessage(humidity: Int): String {
        return when (humidity) {
            in 0..20 -> "Muy baja"
            in 21..40 -> "Baja"
            in 41..60 -> "Moderada"
            in 61..80 -> "Alta"
            else -> "Muy alta"
        }
    }

    fun mapAirQualityToData(airQuality: String): Pair<ImageVector, String> {
        return when (airQuality) {
            "Muy buena" -> Pair(Icons.Default.SentimentVerySatisfied, "Muy buena")
            "Buena" -> Pair(Icons.Default.SentimentSatisfied, "Buena")
            "Moderada" -> Pair(Icons.Default.SentimentNeutral, "Moderada")
            "Mala" -> Pair(Icons.Default.SentimentDissatisfied, "Mala")
            else -> Pair(Icons.Default.SentimentVeryDissatisfied, "Muy mala")
        }
    }
}