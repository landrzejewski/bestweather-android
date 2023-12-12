package pl.training.bestweather.forecast.adapters.provider.persistence

import pl.training.bestweather.forecast.domain.DayForecast
import java.util.Date

class ForecastRoomMapper {

    fun toEntity(city: String) = CityEntity(city)

    fun toEntity(city: String, dayForecast: DayForecast) = with(dayForecast) {
        DayForecastEntity(null, iconName, description, temperature, pressure, date.time, city)
    }

    fun toModel(dayForecastEntity: DayForecastEntity) = with(dayForecastEntity) {
        DayForecast(iconName, description, temperature, pressure, Date(date))
    }

}