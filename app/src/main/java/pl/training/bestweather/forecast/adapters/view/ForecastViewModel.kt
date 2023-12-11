package pl.training.bestweather.forecast.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.bestweather.forecast.adapters.provider.FakeForecastProvider
import pl.training.bestweather.forecast.commons.formatDate
import pl.training.bestweather.forecast.commons.formatPressure
import pl.training.bestweather.forecast.commons.formatTemperature
import pl.training.bestweather.forecast.domain.DayForecast
import pl.training.bestweather.forecast.domain.Forecast

class ForecastViewModel : ViewModel() {

    private val forecastService = Forecast(FakeForecastProvider())

    private val forecastData = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = forecastData

    fun refreshForecastFor(city: String) {
        viewModelScope.launch {
            val data = forecastService.getFor(city).map(::toViewModel)
            forecastData.postValue(data)
        }
    }

    private fun toViewModel(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastViewModel(iconName, description, formatTemperature(temperature), formatPressure(pressure), formatDate(date))
    }

}