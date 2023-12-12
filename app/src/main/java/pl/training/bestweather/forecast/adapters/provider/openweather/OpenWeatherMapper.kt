package pl.training.bestweather.forecast.adapters.provider.openweather

import pl.training.bestweather.forecast.domain.DayForecast
import java.util.Date

class OpenWeatherMapper {

    private val icons = mapOf(
        "01d" to "ic_sun",
        "02d" to "ic_cloud_sun",
        "03d" to "ic_cloud",
        "04d" to "ic_cloud",
        "09d" to "ic_cloud_rain",
        "10d" to "ic_cloud_sun_rain",
        "11d" to "ic_bolt",
        "13d" to "ic_snowflake",
        "50d" to "ic_wind"
    )

    fun toModel(dayForecastDto: DayForecastDto) = with(dayForecastDto) {
        val forecastData = weather.first()
        val iconName = icons[forecastData.icon] ?: "ic_sun"
        DayForecast(iconName, forecastData.description, temperature.day, pressure, Date(date * 1_000))
    }

}