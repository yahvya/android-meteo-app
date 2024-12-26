package yahvya.meteo_app.viewmodels

import androidx.lifecycle.ViewModel
import yahvya.meteo_app.dtos.WeatherDto

/**
 * @brief app model
 */
class MainModel : ViewModel(){
    /**
     * @brief weather dto instance to transmit between pages
     */
    private var chosenWeatherDto: WeatherDto? = null

    /**
     * @brief update the weather  dto instance to transmit between pages
     */
    fun setChosenWeather(weatherDto: WeatherDto): MainModel{
        this.chosenWeatherDto = weatherDto
        return this
    }

    /**
     * @return the weather dto instance to transmit between pages
     */
    fun getChosenWeather():WeatherDto? = this.chosenWeatherDto
}