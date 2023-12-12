package pl.training.bestweather.forecast.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.training.bestweather.commons.store.Store
import pl.training.bestweather.forecast.domain.DayForecast
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
        viewModelScope.launch {
            val city = store.get(CITY_KEY)
            onForecastLoaded(forecastService.getCachedFor(city))
        }
    }

    fun refreshForecastFor(city: String) {
        viewModelScope.launch {
            val data = forecastService.getFor(city)
            store.set(CITY_KEY, city)
            onForecastLoaded(data)
        }
    }

    private fun onForecastLoaded(data: List<DayForecast>) {
        if (data.isNotEmpty()) {
            mutableForecast.postValue(data.map(mapper::toViewModel))
        }
    }

    companion object {

        const val CITY_KEY = "city"

    }

}