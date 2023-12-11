package pl.training.bestweather.forecast.commons

import android.widget.ImageView
import androidx.core.content.ContextCompat

fun ImageView.setDrawable(name: String) {
    val imageId = resources.getIdentifier(name, "drawable", context.opPackageName)
    setImageDrawable(ContextCompat.getDrawable(context, imageId))
}