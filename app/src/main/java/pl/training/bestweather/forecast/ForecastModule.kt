package pl.training.bestweather.forecast

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pl.training.bestweather.BestWeatherDatabase
import pl.training.bestweather.forecast.adapters.provider.FakeForecastProvider
import pl.training.bestweather.forecast.adapters.provider.openweather.OpenWeatherAdapter
import pl.training.bestweather.forecast.adapters.provider.openweather.OpenWeatherApi
import pl.training.bestweather.forecast.adapters.provider.openweather.OpenWeatherMapper
import pl.training.bestweather.forecast.adapters.provider.persistence.ForecastDao
import pl.training.bestweather.forecast.adapters.provider.persistence.ForecastRoomAdapter
import pl.training.bestweather.forecast.adapters.provider.persistence.ForecastRoomMapper
import pl.training.bestweather.forecast.adapters.view.ForecastViewModelMapper
import pl.training.bestweather.forecast.ports.ForecastRepository
import pl.training.bestweather.forecast.domain.ForecastService
import pl.training.bestweather.forecast.ports.ForecastProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.BINARY

@Module
@InstallIn(SingletonComponent::class)
class ForecastModule {

    @Fake
    @Singleton
    @Provides
    fun fakeForecastProvider(): ForecastProvider = FakeForecastProvider()

    @Singleton
    @Provides
    fun openWeatherApi(okHttpClient: OkHttpClient): OpenWeatherApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OpenWeatherApi::class.java)

    @Singleton
    @Provides
    fun openWeatherMapper(): OpenWeatherMapper = OpenWeatherMapper()

    @OpenWeather
    @Singleton
    @Provides
    fun openWeatherForecastProvider(openWeatherApi: OpenWeatherApi, openWeatherMapper: OpenWeatherMapper): ForecastProvider
        = OpenWeatherAdapter(openWeatherApi, openWeatherMapper)

    @Singleton
    @Provides
    fun forecastViewModelMapper(): ForecastViewModelMapper = ForecastViewModelMapper()

    @Singleton
    @Provides
    fun forecastDao(database: BestWeatherDatabase): ForecastDao = database.forecastDao()

    @Singleton
    @Provides
    fun forecastRoomMapper(): ForecastRoomMapper = ForecastRoomMapper()

    @Singleton
    @Provides
    fun forecastRoomRepository(forecastDao: ForecastDao, forecastRoomMapper: ForecastRoomMapper): ForecastRepository
        = ForecastRoomAdapter(forecastDao, forecastRoomMapper)

    @Singleton
    @Provides
    fun forecastService(@OpenWeather forecastProvider: ForecastProvider, forecastRepository: ForecastRepository): ForecastService
        = ForecastService(forecastProvider, forecastRepository)

}

@Qualifier
@Retention(BINARY)
annotation class Fake


@Qualifier
@Retention(BINARY)
annotation class OpenWeather