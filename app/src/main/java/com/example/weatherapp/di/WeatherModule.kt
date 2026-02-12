package com.example.weatherapp.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherModule {

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(WeatherApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    private fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepository(weatherApi)
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
                    val retrofit = provideRetrofit()
                    val api = provideWeatherApi(retrofit)
                    val repository = provideWeatherRepository(api)
                    return WeatherViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}