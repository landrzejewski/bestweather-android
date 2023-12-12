package pl.training.bestweather.forecast.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.training.bestweather.commons.localstorage.PropertiesMap
import pl.training.bestweather.forecast.domain.ForecastService
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastService: ForecastService,
    private val mapper: ForecastViewModelMapper,
    private val propertiesMap: PropertiesMap
) : ViewModel() {

    private val mutableForecast = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = mutableForecast

    init {
        val city = propertiesMap.get(CITY_KEY)
        if (city.isNotBlank()) {
            refreshForecastFor(city)
        }
    }

    fun refreshForecastFor(city: String) {
        viewModelScope.launch {
            val data = forecastService.getFor(city)
                .map(mapper::toViewModel)
            propertiesMap.set(CITY_KEY, city)
            onForecastLoaded(data)
        }
    }

    private fun onForecastLoaded(data: List<DayForecastViewModel>) {
        if (data.isNotEmpty()) {
            mutableForecast.postValue(data)
        }
    }

    companion object {

        const val CITY_KEY = "city"

    }

}