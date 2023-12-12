package pl.training.bestweather.forecast.adapters.provider.openweather

import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("list") val forecast: List<DayForecastDto>
)