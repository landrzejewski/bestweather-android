package pl.training.bestweather

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import pl.training.bestweather.commons.store.Store
import pl.training.bestweather.commons.store.SharedPreferencesStore
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
    fun store(@ApplicationContext context: Context): Store = SharedPreferencesStore(context)

    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): BestWeatherDatabase = Room
        .databaseBuilder(context, BestWeatherDatabase::class.java, "best-weather")
        .fallbackToDestructiveMigration()
        .build()

}