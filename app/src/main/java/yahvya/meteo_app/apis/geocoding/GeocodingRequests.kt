package yahvya.meteo_app.apis.geocoding

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @brief geocoding api requests description
 */
interface GeocodingRequests {
    /**
     * @brief find cities
     */
    @GET(value="search/?language=fr")
    suspend fun getCities(
        @Query(value="name") placeName: String
    ):CityResultDto?
}