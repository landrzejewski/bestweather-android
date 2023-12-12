package pl.training.bestweather.forecast.adapters.provider.persistence

import pl.training.bestweather.forecast.domain.DayForecast
import pl.training.bestweather.forecast.domain.ForecastRepository

class ForecastRoomAdapter(private val dao: ForecastDao, private val mapper: ForecastRoomMapper) : ForecastRepository {

    override suspend fun save(city: String, forecast: List<DayForecast>) {
        dao.save(mapper.toEntity(city))
        dao.save(forecast.map { mapper.toEntity(city, it) })
    }

    override suspend fun findAll(city: String) = dao.findAll(city)?.forecast?.map(mapper::toModel) ?: emptyList()

    override suspend fun deleteAll(city: String) {
        dao.delete(mapper.toEntity(city))
        dao.deleteAll()
    }

}