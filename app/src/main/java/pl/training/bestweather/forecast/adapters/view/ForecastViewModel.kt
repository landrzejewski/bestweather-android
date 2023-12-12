package pl.training.bestweather.forecast.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.bestweather.forecast.adapters.provider.FakeForecastProvider
import pl.training.bestweather.commons.formatDate
import pl.training.bestweather.commons.formatPressure
import pl.training.bestweather.commons.formatTemperature
import pl.training.bestweather.forecast.domain.DayForecast
import pl.training.bestweather.forecast.domain.ForecastService

class ForecastViewModel : ViewModel() {

    private val forecastService = ForecastService(FakeForecastProvider())
    private val mapper = ForecastViewModelMapper()
    private val mutableForecast = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = mutableForecast

    fun refreshForecastFor(city: String) {
        viewModelScope.launch {
            val data = forecastService.getFor(city)
                .map(mapper::toViewModel)
            mutableForecast.postValue(data)
        }
    }

}