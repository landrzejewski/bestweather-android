package pl.training.bestweather.forecast.adapters.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.training.bestweather.R

class ForecastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
    }
}