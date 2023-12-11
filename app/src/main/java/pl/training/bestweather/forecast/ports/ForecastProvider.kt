package pl.training.bestweather.forecast.ports

import pl.training.bestweather.forecast.domain.DayForecast

interface ForecastProvider {

    suspend fun getFor(city: String): List<DayForecast>

}