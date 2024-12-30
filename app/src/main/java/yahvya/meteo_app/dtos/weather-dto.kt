package yahvya.meteo_app.dtos

import yahvya.meteo_app.apis.openweather.OpenWeatherDto
import java.time.LocalDate

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
    /**
     * @brief wind speed measure
     */
    val windSpeed: String,
    /**
     * @brief weather code
     */
    val weatherCode: String
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
        fun fromOpenWeatherDto(openWeatherDto: OpenWeatherDto,placeDisplayName: String = "Votre position"):WeatherDto{
            val temperatureMeasures:MutableList<TimeWeatherData> = mutableListOf()

            // convert measures to TimeWeather data
            openWeatherDto.daily.time.forEachIndexed{ index,value ->
                temperatureMeasures.add(TimeWeatherData(
                    time= value,
                    temperature = "Non défini",
                    temperatureMax = openWeatherDto.daily.temperature_2m_max[index].toString(),
                    temperatureMin = openWeatherDto.daily.temperature_2m_min[index].toString(),
                    windSpeed = openWeatherDto.daily.wind_speed_10m_max[index].toString(),
                    weatherCode = openWeatherDto.daily.weather_code[index].toString()
                ))
            }

            // add today
            temperatureMeasures.add(TimeWeatherData(
                time= LocalDate.now().toString(),
                temperature = openWeatherDto.current.temperature_2m.toString(),
                weatherCode = openWeatherDto.current.weather_code.toString(),
                temperatureMin = openWeatherDto.daily.temperature_2m_min.first().toString(),
                temperatureMax = openWeatherDto.daily.temperature_2m_max.first().toString(),
                windSpeed = openWeatherDto.current.wind_speed_10m.toString()
            ))

            return WeatherDto(
                placeName = placeDisplayName,
                longitude = openWeatherDto.longitude.toString(),
                latitude = openWeatherDto.latitude.toString(),
                temperatureUnit = openWeatherDto.current_units.temperature_2m,
                windSpeedUnit = openWeatherDto.current_units.wind_speed_10m,
                temperatureMeasures = temperatureMeasures
            )
        }

        /**
         * @param code weather code
         * @return the description from weather code
         * @attention i used chatgpt to generate the map from open meteo documentation
         */
        fun findDescriptionFromWeatherCode(code: String): String {
            val weatherDescriptionMap = mapOf(
                "0" to "Ciel clair",
                "1" to "Principalement clair",
                "2" to "Partiellement nuageux",
                "3" to "Couvert",
                "45" to "Brouillard",
                "48" to "Brouillard givrant",
                "51" to "Bruine légère",
                "53" to "Bruine modérée",
                "55" to "Bruine forte",
                "56" to "Bruine verglaçante légère",
                "57" to "Bruine verglaçante forte",
                "61" to "Pluie faible",
                "63" to "Pluie modérée",
                "65" to "Pluie forte",
                "66" to "Pluie verglaçante légère",
                "67" to "Pluie verglaçante forte",
                "71" to "Chute de neige faible",
                "73" to "Chute de neige modérée",
                "75" to "Chute de neige forte",
                "77" to "Grains de neige",
                "80" to "Averses de pluie faibles",
                "81" to "Averses de pluie modérées",
                "82" to "Averses de pluie violentes",
                "85" to "Averses de neige faibles",
                "86" to "Averses de neige fortes",
                "95" to "Orage faible ou modéré",
                "96" to "Orage avec grêle légère",
                "99" to "Orage avec grêle forte"
            )

            return weatherDescriptionMap[code] ?: "Code inconnu"
        }

    }
}