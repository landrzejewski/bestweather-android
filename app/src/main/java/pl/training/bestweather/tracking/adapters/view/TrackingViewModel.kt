package pl.training.bestweather.tracking.adapters.view

import androidx.lifecycle.ViewModel
import pl.training.bestweather.commons.formatPace
import pl.training.bestweather.commons.formatSpeed
import pl.training.bestweather.commons.formatTime
import pl.training.bestweather.tracking.domain.ActivityService
import pl.training.bestweather.tracking.domain.Position

class TrackingViewModel(private val activityService: ActivityService) : ViewModel() {

    var speed = ""
    var pace = ""
    var duration = ""

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