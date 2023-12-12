package pl.training.bestweather

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.training.bestweather.forecast.adapters.provider.persistence.CityEntity
import pl.training.bestweather.forecast.adapters.provider.persistence.DayForecastEntity
import pl.training.bestweather.forecast.adapters.provider.persistence.ForecastDao

@Database(entities = [CityEntity::class, DayForecastEntity::class], version = 1, exportSchema = false)
abstract class BestWeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

}