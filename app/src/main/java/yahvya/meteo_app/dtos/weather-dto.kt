package yahvya.meteo_app.dtos

import yahvya.meteo_app.apis.openweather.OpenWeatherDto

/**
 * @brief weather data for a specific hour
 */
data class TimeWeatherData(
    /**
     * @brief associated date
     */
    val time: String,
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
     * @brief temperature measures
     */
    val temperatureMeasures: MutableList<TimeWeatherData>
){
    companion object{
        /**
         * @brief create app weather dto from open weather data
         * @param openWeatherDto open weather dto
         * @param placeDisplayName place display name
         */
        fun fromOpenWeatherDto(openWeatherDto: OpenWeatherDto,placeDisplayName: String = "Votre position"):WeatherDto = WeatherDto(
            placeName = placeDisplayName,
            longitude = openWeatherDto.longitude.toString(),
            latitude = openWeatherDto.latitude.toString(),
            temperatureUnit = openWeatherDto.current_units.temperature_2m,
            windSpeedUnit = openWeatherDto.current_units.wind_speed_10m,
            temperatureMeasures = mutableListOf()
        )
    }
}