package pl.training.bestweather.forecast.adapters.provider.openweather

import android.util.Log
import pl.training.bestweather.forecast.ports.ForecastProvider

class OpenWeatherAdapter(private val openWeatherApi: OpenWeatherApi, private val mapper: OpenWeatherMapper) : ForecastProvider {

    private val tag = OpenWeatherMapper::class.java.name

    override suspend fun getFor(city: String) = try {
        openWeatherApi.getForecastFor(city)
            .forecast
            .map(mapper::toModel)
    } catch (exception: Exception) {
        Log.w(tag, "Fetching forecast failed: " + exception.message)
        emptyList()
    }

}