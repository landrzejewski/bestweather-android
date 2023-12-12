package pl.training.bestweather.forecast.adapters.provider.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey val name: String
)