package pl.training.bestweather

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import pl.training.bestweather.commons.localstorage.PropertiesMap
import pl.training.bestweather.commons.localstorage.SharedPreferencesPropertiesMap
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BestWeatherModule {

    @Singleton
    @Provides
    fun httpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = BASIC
        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun propertiesMap(@ApplicationContext context: Context): PropertiesMap = SharedPreferencesPropertiesMap(context)

}