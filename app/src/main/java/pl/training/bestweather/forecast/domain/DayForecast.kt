package pl.training.bestweather.forecast.domain

import java.util.Date

data class DayForecast(
    val iconName: String,
    val description: String,
    val temperature: Double,
    val pressure: Double,
    val date: Date
)