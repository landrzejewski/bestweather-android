package pl.training.bestweather.forecast.adapters.provider.openweather

import com.google.gson.annotations.SerializedName

data class DayForecastDto(
    @SerializedName("temp") val temperature: TemperatureDto,
    val pressure: Double,
    @SerializedName("dt") val date: Long,
    val weather: List<WeatherDto>
)