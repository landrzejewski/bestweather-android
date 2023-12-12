package pl.training.bestweather.commons

import pl.training.bestweather.forecast.adapters.provider.FakeForecastProvider
import pl.training.bestweather.forecast.adapters.provider.openweather.OpenWeatherAdapter
import pl.training.bestweather.forecast.adapters.provider.openweather.OpenWeatherApi
import pl.training.bestweather.forecast.adapters.provider.openweather.OpenWeatherMapper
import pl.training.bestweather.forecast.adapters.view.ForecastViewModelMapper
import pl.training.bestweather.forecast.domain.ForecastService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val fakeForecastProvider = FakeForecastProvider()

private val openWeatherApi = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/data/2.5/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(OpenWeatherApi::class.java)

private val openWeatherMapper = OpenWeatherMapper()

private val openWeatherAdapter = OpenWeatherAdapter(openWeatherApi, openWeatherMapper)

val forecastViewModelMapper = ForecastViewModelMapper()

fun forecastService() = ForecastService(openWeatherAdapter)

