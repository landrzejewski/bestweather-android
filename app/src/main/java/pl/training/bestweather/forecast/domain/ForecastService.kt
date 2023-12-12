package pl.training.bestweather.forecast.domain

import pl.training.bestweather.forecast.ports.ForecastProvider

class ForecastService(private val forecastProvider: ForecastProvider, private val forecastRepository: ForecastRepository) {

    suspend fun getFor(city: String): List<DayForecast> {
        val forecast = forecastProvider.getFor(city)
        forecastRepository.deleteAll(city)
        forecastRepository.save(city, forecast)
        return forecast
    }

    suspend fun getCachedFor(city: String) = forecastRepository.findAll(city)

}