package pl.training.bestweather.forecast.domain

import pl.training.bestweather.forecast.ports.ForecastProvider

class ForecastService(private val forecastProvider: ForecastProvider) {

    suspend fun getFor(city: String) = forecastProvider.getFor(city)

}