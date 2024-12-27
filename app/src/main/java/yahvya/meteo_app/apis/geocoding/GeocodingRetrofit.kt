package yahvya.meteo_app.apis.geocoding

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @brief retrofit instance builder
 */
data object GeocodingRetrofit{
    val INSTANCE: GeocodingRequests by lazy {
        Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingRequests::class.java)
    }
}
