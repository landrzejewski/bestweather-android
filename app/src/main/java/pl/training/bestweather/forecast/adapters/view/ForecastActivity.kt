package pl.training.bestweather.forecast.adapters.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import pl.training.bestweather.databinding.ActivityForecastBinding
import pl.training.bestweather.commons.hideKeyboard
import pl.training.bestweather.commons.setDrawable

class ForecastActivity : AppCompatActivity() {

    private val viewModel: ForecastViewModel by viewModels()
    private val forecastRecyclerViewAdapter = ForecastRecyclerViewAdapter()
    private lateinit var binding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        bind()
    }

    private fun init() {
        binding.nextDaysForecast.layoutManager = LinearLayoutManager(this, HORIZONTAL, false)
        binding.nextDaysForecast.adapter = forecastRecyclerViewAdapter
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
        forecastRecyclerViewAdapter.update(forecast.drop(1))
    }

}