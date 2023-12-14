package pl.training.bestweather.tracking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.training.bestweather.tracking.domain.ActivityService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ForecastModule {

    @Singleton
    @Provides
    fun activityService(): ActivityService = ActivityService()

}