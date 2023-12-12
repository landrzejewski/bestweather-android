package pl.training.bestweather.forecast.domain

interface ForecastRepository {

    suspend fun save(city: String, forecast: List<DayForecast>)

    suspend fun findAll(city: String): List<DayForecast>

    suspend fun deleteAll(city: String)

}