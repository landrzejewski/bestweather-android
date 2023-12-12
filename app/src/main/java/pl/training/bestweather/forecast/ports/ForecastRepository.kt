package pl.training.bestweather.forecast.ports

import pl.training.bestweather.forecast.domain.DayForecast

interface ForecastRepository {

    suspend fun save(city: String, forecast: List<DayForecast>)

    suspend fun findAll(city: String): List<DayForecast>

    suspend fun deleteAll(city: String)

}