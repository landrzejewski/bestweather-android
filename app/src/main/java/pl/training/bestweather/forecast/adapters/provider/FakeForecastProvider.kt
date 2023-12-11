package pl.training.bestweather.forecast.adapters.provider

import pl.training.bestweather.forecast.domain.DayForecast
import pl.training.bestweather.forecast.ports.ForecastProvider
import java.util.Date

class FakeForecastProvider : ForecastProvider {

    override suspend fun getFor(city: String) = listOf(
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date())
    )

}