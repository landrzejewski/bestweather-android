package pl.training.bestweather.forecast.adapters.view

import pl.training.bestweather.commons.formatDate
import pl.training.bestweather.commons.formatPressure
import pl.training.bestweather.commons.formatTemperature
import pl.training.bestweather.forecast.domain.DayForecast

class ForecastViewModelMapper {

    fun toViewModel(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastViewModel(iconName, description, formatTemperature(temperature), formatPressure(pressure), formatDate(date))
    }

}