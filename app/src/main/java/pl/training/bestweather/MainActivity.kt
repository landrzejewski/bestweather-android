package pl.training.bestweather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.training.bestweather.forecast.adapters.view.ForecastViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Forecast()
        }
    }

    @Composable
    fun Forecast(viewModel: ForecastViewModel = viewModel()) {
        Text(text = "Forecast")
    }

}