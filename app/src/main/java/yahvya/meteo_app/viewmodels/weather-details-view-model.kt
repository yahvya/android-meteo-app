package yahvya.meteo_app.viewmodels

import androidx.lifecycle.ViewModel
import yahvya.meteo_app.dtos.WeatherDto

/**
 * @brief weather details view model
 */
class WeatherDetailsViewModel : ViewModel() {
    /**
     * @brief dto to show the details of
     */
    var weatherDto: WeatherDto? = null
}