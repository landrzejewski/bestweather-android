package pl.training.bestweather.forecast.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.training.bestweather.commons.store.Store
import pl.training.bestweather.forecast.domain.ForecastService
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastService: ForecastService,
    private val mapper: ForecastViewModelMapper,
    private val store: Store
) : ViewModel() {

    private val mutableForecast = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = mutableForecast

    init {
        val city = store.get(CITY_KEY)
        viewModelScope.launch {
            val forecast = forecastService.getCachedFor(city).map(mapper::toViewModel)
            update(forecast)
        }
    }

    fun refreshForecastFor(city: String) {
        viewModelScope.launch {
            val forecast = forecastService.getFor(city).map(mapper::toViewModel)
            store.set(CITY_KEY, city)
            update(forecast)
        }
    }

    private fun update(forecast: List<DayForecastViewModel>) {
        if (forecast.isNotEmpty()) {
            mutableForecast.postValue(forecast)
        }
    }

    companion object {

        const val CITY_KEY = "city"

    }

}