package pl.training.bestweather.forecast.commons

import java.text.SimpleDateFormat
import java.util.*

fun formatTemperature(value: Double) = "${value.toInt()}°"

fun formatPressure(value: Double) = "${value.toInt()} hPa"

fun formatDate(date: Date, format: String = "dd/MM") = SimpleDateFormat(format, Locale.getDefault()).format(date)