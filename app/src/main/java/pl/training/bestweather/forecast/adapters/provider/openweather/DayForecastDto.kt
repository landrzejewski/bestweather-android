package pl.training.goodweather.forecast.adapter.provider.openweathermap

import com.google.gson.annotations.SerializedName

data class DayForecastDto(
    @SerializedName("temp") val temperature: TemperatureDto,
    val pressure: Double,
    @SerializedName("dt") val date: Long,
    val weather: List<WeatherDto>
)