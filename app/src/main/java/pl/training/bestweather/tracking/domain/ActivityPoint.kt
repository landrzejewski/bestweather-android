package pl.training.bestweather.tracking.domain

data class ActivityPoint(
    val id: String,
    val activityId: String,
    val timestamp: Long,
    val distance: Float,
    val speed: Float,
    val pace: Double,
    val duration: Long,
    val position: Position
)