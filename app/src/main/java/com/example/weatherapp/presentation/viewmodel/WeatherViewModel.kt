package com.example.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WeatherState {
    data object Idle : WeatherState()
    data object Loading : WeatherState()
    data class Success(val weather: Weather) : WeatherState()
    data class Error(val message: String) : WeatherState()
    data object LocationPermissionRequired : WeatherState()
}

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Idle)

    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun fetchWeatherData(latitude: Double, longitude: Double, locationName: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading

            val result = weatherRepository.getWeatherData(latitude, longitude, locationName)

            _weatherState.value = if (result.isSuccess) {
                WeatherState.Success(result.getOrThrow())
            } else {
                WeatherState.Error(
                    result.exceptionOrNull()?.message ?: "Error desconocido al cargar datos del clima"
                )
            }
        }
    }

    fun requestLocationPermission() {
        _weatherState.value = WeatherState.LocationPermissionRequired
    }

    fun resetToIdle() {
        _weatherState.value = WeatherState.Idle
    }
}