package com.example.weatherapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.presentation.ui.theme.Afacad
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import com.example.weatherapp.data.model.HourlyForecast
import com.example.weatherapp.util.WeatherCodeMapper

@Composable
fun DailyForecast(hourlyForecast: List<HourlyForecast> = emptyList()) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_clock),
                contentDescription = "Clock icon",
                tint = Color.White,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Pronóstico diario",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = Afacad,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow {
            items(hourlyForecast) { forecast ->
                DailyForecastCard(
                    time = forecast.time,
                    temp = forecast.temperature.toInt(),
                    weatherCode = forecast.weatherCode
                )
            }
        }
    }
}

@Composable
fun DailyForecastCard(time: String, temp: Int = 32, weatherCode: Int = 3) {
    Column(
        modifier = Modifier
            .padding(end = 12.dp)
            .width(84.dp)
            .height(122.dp)
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = WeatherCodeMapper.getIconResource(weatherCode)),
            contentDescription = null,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = time,
            color = Color.White,
            fontFamily = Afacad,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "$temp°",
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = Afacad,
            fontWeight = FontWeight.Bold
        )
    }
}