package pl.training.bestweather.forecast.adapters.provider.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DayForecastEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "icon_name") val iconName: String,
    val description: String,
    val temperature: Double,
    val pressure: Double,
    val date: Long,
    val cityName: String
)