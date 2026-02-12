package com.example.weatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.di.WeatherModule
import com.example.weatherapp.presentation.ui.components.*
import com.example.weatherapp.presentation.ui.theme.GradientEnd
import com.example.weatherapp.presentation.ui.theme.GradientMiddle
import com.example.weatherapp.presentation.ui.theme.GradientStart
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.example.weatherapp.presentation.viewmodel.WeatherState
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.values.any { it }) {
            fetchLocationAndWeather()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar ViewModel
        val factory = WeatherModule.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]

        // Inicializar cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            WeatherAppTheme {
                WeatherScreen()
            }
        }

        // Verificar permisos al iniciar
        if (hasLocationPermission()) {
            fetchLocationAndWeather()
        } else {
            viewModel.requestLocationPermission()
        }
    }

    @Composable
    private fun WeatherScreen() {
        val weatherState by viewModel.weatherState.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GradientStart, GradientMiddle, GradientEnd)
                    )
                )
        ) {
            when (val state = weatherState) {
                is WeatherState.LocationPermissionRequired -> {
                    LocationPermissionDialog(
                        onRequestPermission = {
                            locationPermissionLauncher.launch(REQUIRED_PERMISSIONS)
                        }
                    )
                }
                is WeatherState.Loading -> {
                    LoadingScreen()
                }
                is WeatherState.Error -> {
                    ErrorDialog(
                        message = state.message,
                        onRetry = {
                            fetchLocationAndWeather()
                        }
                    )
                }
                is WeatherState.Success -> {
                    WeatherContent(weather = state.weather)
                }
                is WeatherState.Idle -> {
                    // Estado inicial
                }
            }
        }
    }

    @Composable
    private fun LocationPermissionDialog(onRequestPermission: () -> Unit) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "Permisos de ubicación necesarios") },
            text = { Text(text = "Esta aplicación necesita acceso a tu ubicación para mostrar el clima actual de tu zona.") },
            confirmButton = {
                TextButton(onClick = onRequestPermission) {
                    Text("Conceder permisos")
                }
            }
        )
    }

    @Composable
    private fun WeatherContent(weather: Weather) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            MainWeatherCard(
                location = weather.location,
                currentTemp = weather.currentTemp.toInt(),
                weatherDescription = weather.weatherDescription,
                feelsLike = weather.feelsLike.toInt(),
                weatherCode = weather.weatherCode
            )

            Spacer(modifier = Modifier.height(16.dp))

            HourlyForecast(hourlyForecast = weather.hourlyForecast)

            Spacer(modifier = Modifier.height(16.dp))

            WeeklyForecast(dailyForecast = weather.dailyForecast)

            Spacer(modifier = Modifier.height(16.dp))

            DetailsCards(
                uvIndex = weather.uvIndex.toInt(),
                humidity = weather.humidity,
                windSpeed = weather.windSpeed.toInt(),
                airQuality = weather.airQuality
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    private fun hasLocationPermission(): Boolean {
        return REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun fetchLocationAndWeather() {
        lifecycleScope.launch {
            try {
                val location = getCurrentLocation()
                if (location != null) {
                    val locationName = getLocationName(location.latitude, location.longitude)
                    viewModel.fetchWeatherData(
                        location.latitude,
                        location.longitude,
                        locationName
                    )
                }
            } catch (e: Exception) {
                viewModel.requestLocationPermission()
            }
        }
    }

    private suspend fun getCurrentLocation(): Location? = suspendCancellableCoroutine { continuation ->
        if (!hasLocationPermission()) {
            continuation.resume(null)
            return@suspendCancellableCoroutine
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000L
        ).apply {
            setMinUpdateIntervalMillis(5000L)
            setMaxUpdates(1)
        }.build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    continuation.resume(location)
                }
                fusedLocationClient.removeLocationUpdates(this)
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            continuation.resume(null)
        }

        continuation.invokeOnCancellation {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    private suspend fun getLocationName(latitude: Double, longitude: Double): String {
        return withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(this@MainActivity)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    suspendCancellableCoroutine { continuation ->
                        geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                            if (addresses.isNotEmpty()) {
                                val address = addresses[0]
                                val locationName = buildLocationString(
                                    address.locality,
                                    address.adminArea,
                                    address.countryName
                                )
                                continuation.resume(locationName)
                            } else {
                                continuation.resume(formatCoordinates(latitude, longitude))
                            }
                        }
                    }
                } else {
                    @Suppress("DEPRECATION")
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        buildLocationString(
                            address.locality,
                            address.adminArea,
                            address.countryName
                        )
                    } else {
                        formatCoordinates(latitude, longitude)
                    }
                }
            } catch (e: Exception) {
                formatCoordinates(latitude, longitude)
            }
        }
    }

    private fun buildLocationString(locality: String?, adminArea: String?, country: String?): String {
        return when {
            !locality.isNullOrEmpty() && !country.isNullOrEmpty() -> "$locality, $country"
            !locality.isNullOrEmpty() && !adminArea.isNullOrEmpty() -> "$locality, $adminArea"
            !locality.isNullOrEmpty() -> locality
            !adminArea.isNullOrEmpty() && !country.isNullOrEmpty() -> "$adminArea, $country"
            !adminArea.isNullOrEmpty() -> adminArea
            !country.isNullOrEmpty() -> country
            else -> "Ubicación desconocida"
        }
    }

    private fun formatCoordinates(latitude: Double, longitude: Double): String {
        val latDirection = if (latitude >= 0) "N" else "S"
        val lonDirection = if (longitude >= 0) "E" else "O"
        return String.format(
            "%.2f° %s, %.2f° %s",
            kotlin.math.abs(latitude),
            latDirection,
            kotlin.math.abs(longitude),
            lonDirection
        )
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}