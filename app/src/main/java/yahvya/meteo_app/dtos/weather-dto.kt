package yahvya.meteo_app.dtos

/**
 * @brief weather data for a specific hour
 */
data class HourlyWeatherData(
    /**
     * @brief associated date
     */
    val date: String,
    /**
     * @brief associated temperature value
     */
    val temperature: String,
    /**
     * @brief associated minimum temperature value
     */
    val temperatureMin: String,
    /**
     * @brief associated maximum temperature value
     */
    val temperatureMax: String,
    /**
     * @brief rain measure
     */
    val rainMeasure: String,
    /**
     * @brief cloud low measure
     */
    val cloudLowMeasure: String,
    /**
     * @brief cloud high measure
     */
    val cloudHighMeasure: String,
)

/**
 * @brief weather expected data
 */
data class WeatherDto(
    /**
     * @brief place name
     */
    val placeName:String,
    /**
     * @brief place longitude
     */
    val longitude:String,
    /**
     * @brief place latitude
     */
    val latitude:String,
    /**
     * @brief temperature description unit (celcius ...)
     */
    val temperatureUnit: String,
    /**
     * @brief wind speed unit
     */
    val windSpeedUnit: String,
    /**
     * @brief cloud measure unit
     */
    val cloudMeasureUnit: String,
    /**
     * @brief rain measure unit
     */
    val rainMeasureUnit: String,
    /**
     * @brief temperature measures
     */
    val temperatureMeasures: MutableList<HourlyWeatherData>
)