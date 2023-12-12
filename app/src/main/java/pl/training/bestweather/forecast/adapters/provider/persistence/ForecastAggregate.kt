package pl.training.bestweather.forecast.adapters.provider.persistence

import androidx.room.Embedded
import androidx.room.Relation

data class ForecastAggregate(
    @Embedded val city: CityEntity,
    @Relation(parentColumn = "name", entityColumn = "cityName") val forecast: List<DayForecastEntity>
)