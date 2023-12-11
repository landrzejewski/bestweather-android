package pl.training.bestweather.forecast.domain

import pl.training.bestweather.forecast.ports.ForecastProvider

class Forecast(private val forecastProvider: ForecastProvider) {

/*
    suspend fun getFor(city: String): List<DayForecast> {
        return forecastProvider.getFor(city)
    }
*/

    suspend fun getFor(city: String) = forecastProvider.getFor(city)

}