package yahvya.meteo_app.apis.openweather

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @brief open weather request
 */
interface OpenWeatherRequests {
    @GET(value="forecast?current=temperature_2m,weather_code,wind_speed_10m&daily=weather_code,temperature_2m_max,temperature_2m_min,wind_speed_10m_max&models=meteofrance_seamless")
    suspend fun getWeatherOf(
        @Query(value = "longitude") longitude: Double,
        @Query(value= "latitude") latitude: Double
    ): OpenWeatherDto?
}