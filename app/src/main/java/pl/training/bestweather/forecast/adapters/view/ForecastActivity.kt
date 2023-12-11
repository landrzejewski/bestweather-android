package pl.training.bestweather.forecast.adapters.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import pl.training.bestweather.R
import pl.training.bestweather.databinding.ActivityForecastBinding
import pl.training.bestweather.forecast.commons.setDrawable

class ForecastActivity : AppCompatActivity() {

    private val viewModel: ForecastViewModel by viewModels()
    private lateinit var binding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
    }

    private fun bind() {
        viewModel.forecast.observe(this, ::updateView)
        binding.checkBtn.setOnClickListener {
            val city = binding.cityName.text.toString()
            viewModel.refreshForecastFor(city)
        }
    }

    private fun updateView(forecast: List<DayForecastViewModel>) {
        val currentForecast = forecast.first()
        binding.icon.setDrawable(currentForecast.iconName)
    }

}