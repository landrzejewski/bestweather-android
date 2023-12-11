package pl.training.bestweather.forecast.adapters.view

data class DayForecastViewModel(
    val iconName: String,
    val description: String,
    val temperature: String,
    val pressure: String,
    val date: String
)