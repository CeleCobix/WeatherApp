package com.example.weatherapp.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.util.WeatherCodeMapper

@Composable
fun DetailsCards(
    uvIndex: Int,
    humidity: Int,
    windSpeed: Int,
    airQuality: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_details),
                contentDescription = "Detalles",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Detalles", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            UvIndexCard(uvIndex = uvIndex)
            HumidityCard(humidity = humidity)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WindCard(windSpeed = windSpeed)
            AirQualityCard(airQuality = airQuality)
        }
    }
}

@Composable
fun UvIndexCard(uvIndex: Int) {
    val progress = uvIndex.toFloat() / 11f
    val uvMessage = WeatherCodeMapper.mapUvIndexToMessage(uvIndex)

    Card(
        modifier = Modifier
            .width(162.dp)
            .height(146.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(10)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_sun),
                    contentDescription = "Índice UV",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Índice UV", color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = uvMessage, color = Color.White, fontSize = 16.sp)
            Text(text = "$uvIndex", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))

            // Barra de gradiente con indicador
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    // Dibujar barra de gradiente (verde a rojo)
                    drawLine(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Green,
                                Color.Yellow,
                                Color(0xFFFFA500),
                                Color.Red,
                                Color(0xFF9932CC)
                            )
                        ),
                        start = Offset(0f, height / 2),
                        end = Offset(width, height / 2),
                        strokeWidth = height,
                        cap = StrokeCap.Round
                    )

                    // Dibujar indicador (bolita)
                    val indicatorPosition = progress * width
                    drawCircle(
                        color = Color.White,
                        radius = height * 1.2f,
                        center = Offset(indicatorPosition, height / 2)
                    )
                }
            }
        }
    }
}

@Composable
fun HumidityCard(humidity: Int) {
    val progress = humidity.toFloat() / 100f
    val humidityMessage = WeatherCodeMapper.mapHumidityToMessage(humidity)

    Card(
        modifier = Modifier
            .width(162.dp)
            .height(146.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(10)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_humidity),
                    contentDescription = "Humedad",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Humedad", color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = humidityMessage, color = Color.White, fontSize = 16.sp)
            Text(text = "$humidity%", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))

            // Barra de humedad (azul cielo)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    // Dibujar barra de fondo
                    drawLine(
                        color = Color.White.copy(alpha = 0.4f),
                        start = Offset(0f, height / 2),
                        end = Offset(width, height / 2),
                        strokeWidth = height,
                        cap = StrokeCap.Round
                    )

                    // Dibujar barra de progreso (azul cielo)
                    drawLine(
                        color = Color(0xFF87CEEB),
                        start = Offset(0f, height / 2),
                        end = Offset(progress * width, height / 2),
                        strokeWidth = height,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Composable
fun WindCard(windSpeed: Int) {
    Card(
        modifier = Modifier
            .width(162.dp)
            .height(146.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(10)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_wind),
                    contentDescription = "Viento",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Viento", color = Color.White)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "$windSpeed km/h", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun AirQualityCard(airQuality: String) {
    val (icon, message) = WeatherCodeMapper.mapAirQualityToData(airQuality)

    Card(
        modifier = Modifier
            .width(164.dp)
            .height(146.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(10)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_airquality),
                    contentDescription = "Calidad del aire",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Calidad del aire", color = Color.White)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = icon, contentDescription = message, tint = Color.White, modifier = Modifier.size(40.dp))
                Text(text = message, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            }
        }
    }
}
