package pl.training.bestweather.tracking.adapters.view

import android.location.Location
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.training.bestweather.commons.formatPace
import pl.training.bestweather.commons.formatSpeed
import pl.training.bestweather.commons.formatTime
import pl.training.bestweather.tracking.domain.ActivityService
import pl.training.bestweather.tracking.domain.Position
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(private val activityService: ActivityService) : ViewModel() {

    var speed = ""
    var pace = ""
    var duration = ""
    var lastLocation: Location? = null

    fun start() {
        activityService.startActivity()
    }

    fun onLocationChange(position: Position, speed: Float, distance: Float) {
        val activityPoint = activityService.createActivityPoint(position, speed, distance)
        this.speed = formatSpeed(activityPoint.speed)
        pace = formatPace(activityPoint.pace)
        duration = formatTime(activityPoint.duration)
    }

}