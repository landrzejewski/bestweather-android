package pl.training.bestweather.forecast.adapters.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import dagger.hilt.android.AndroidEntryPoint
import pl.training.bestweather.R
import pl.training.bestweather.commons.hideKeyboard
import pl.training.bestweather.commons.setDrawable
import pl.training.bestweather.databinding.FragmentForecastBinding

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private val viewModel: ForecastViewModel by activityViewModels()
    private val forecastRecyclerViewAdapter = ForecastRecyclerViewAdapter()
    private lateinit var binding: FragmentForecastBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentForecastBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        bind()
    }

    private fun init() {
        binding.nextDaysForecast.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        binding.nextDaysForecast.adapter = forecastRecyclerViewAdapter
    }

    private fun bind() {
        viewModel.forecast.observe(viewLifecycleOwner, ::updateView)
        binding.checkBtn.setOnClickListener(::onForecastCheck)
        forecastRecyclerViewAdapter.tapListener = {
            viewModel.selectedDayForecast = it
            findNavController().navigate(R.id.show_forecast_details)
        }
    }

    private fun updateView(forecast: List<DayForecastViewModel>) {
        with (forecast.first()) {
            binding.icon.setDrawable(iconName)
            binding.description.text = description
            binding.temperature.text = temperature
            binding.pressure.text = pressure
        }
        forecastRecyclerViewAdapter.update(forecast.dropLast(1))
    }

    private fun onForecastCheck(view: View) {
        view.hideKeyboard()
        val city = binding.cityName.text.toString()
        viewModel.refreshForecastFor(city)
    }

}