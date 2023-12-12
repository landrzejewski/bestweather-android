package pl.training.bestweather.forecast.adapters.provider.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ForecastDao {

    @Insert(onConflict = IGNORE)
    suspend fun save(cityEntity: CityEntity)

    @Insert
    suspend fun save(forecastEntities: List<DayForecastEntity>)

    @Transaction
    @Query("select * from CityEntity where name = :city")
    suspend fun findAll(city: String): ForecastAggregate?

    @Delete
    suspend fun delete(cityEntity: CityEntity)

    @Query("delete from DayForecastEntity")
    suspend fun deleteAll()

}