package pl.training.bestweather.forecast.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.bestweather.commons.forecastService
import pl.training.bestweather.commons.forecastViewModelMapper
import pl.training.bestweather.forecast.domain.DayForecast

class ForecastViewModel : ViewModel() {

    private val forecastService = forecastService()
    private val mapper = forecastViewModelMapper
    private val mutableForecast = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = mutableForecast

    fun refreshForecastFor(city: String) {
        viewModelScope.launch {
            val data = forecastService.getFor(city)
                .map(mapper::toViewModel)
           onForecastLoaded(data)
        }
    }

    private fun onForecastLoaded(data: List<DayForecastViewModel>) {
        if (data.isNotEmpty()) {
            mutableForecast.postValue(data)
        }
    }

}