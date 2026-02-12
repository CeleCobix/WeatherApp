package com.example.weatherapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.presentation.ui.theme.Afacad
import com.example.weatherapp.util.WeatherCodeMapper

@Composable
fun MainWeatherCard(
    location: String,
    currentTemp: Int,
    weatherDescription: String,
    feelsLike: Int,
    weatherCode: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = location,
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = Afacad,
            fontWeight = FontWeight.Bold
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = WeatherCodeMapper.getIconResource(weatherCode)),
                contentDescription = weatherDescription,
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$currentTemp°",
                style = TextStyle(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.White,
                            Color.White.copy(alpha = 0.75f),
                            Color.White.copy(alpha = 0.5f)
                        )
                    )
                ),
                fontSize = 96.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = weatherDescription,
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = Afacad,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sensación térmica $feelsLike°",
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = Afacad,
            fontWeight = FontWeight.Medium
        )
    }
}