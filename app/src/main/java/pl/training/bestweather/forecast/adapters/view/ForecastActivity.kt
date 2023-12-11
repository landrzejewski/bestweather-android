package pl.training.bestweather.forecast.adapters.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import pl.training.bestweather.R
import pl.training.bestweather.databinding.ActivityForecastBinding
import pl.training.bestweather.forecast.commons.hideKeyboard
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
        binding.checkBtn.setOnClickListener(::onForecastCheck)
    }

    private fun onForecastCheck(view: View) {
        val city = binding.cityName.text.toString()
        view.hideKeyboard()
        viewModel.refreshForecastFor(city)
    }

    private fun updateView(forecast: List<DayForecastViewModel>) {
        with (forecast.first()) {
            binding.icon.setDrawable(iconName)
            binding.description.text = description
            binding.temperature.text = temperature
            binding.pressure.text = pressure
        }
    }

}