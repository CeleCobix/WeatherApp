package com.example.weatherapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.example.weatherapp.data.model.DailyForecast
import com.example.weatherapp.util.WeatherCodeMapper

@Composable
fun WeeklyForecast(dailyForecast: List<DailyForecast> = emptyList()) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_clock),
                contentDescription = "Calendar icon",
                tint = Color.White,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Pronóstico semanal",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = Afacad,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow {
            items(dailyForecast) { forecast ->
                WeeklyForecastCard(
                    day = forecast.day,
                    weatherCode = forecast.weatherCode,
                    maxTemp = forecast.maxTemp.toInt(),
                    minTemp = forecast.minTemp.toInt(),
                    precipitationProbability = forecast.precipitationProbability
                )
            }
        }
    }
}

@Composable
fun WeeklyForecastCard(
    day: String,
    weatherCode: Int = 2,
    maxTemp: Int = 33,
    minTemp: Int = 23,
    precipitationProbability: Int = 0
) {
    Column(
        modifier = Modifier
            .padding(end = 12.dp)
            .width(83.dp)
            .height(138.dp)
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = day,
            color = Color.White,
            fontFamily = Afacad,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(id = WeatherCodeMapper.getIconResource(weatherCode)),
            contentDescription = null,
            modifier = Modifier.height(40.dp)
        )
        Text(
            text = "↑$maxTemp° ↓$minTemp°",
            color = Color.White,
            fontFamily = Afacad,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_precipitation),
                contentDescription = null,
                modifier = Modifier.height(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$precipitationProbability%",
                color = Color.White,
                fontFamily = Afacad,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}