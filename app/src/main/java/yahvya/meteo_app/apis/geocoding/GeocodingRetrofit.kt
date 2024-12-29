package yahvya.meteo_app.apis.geocoding

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @brief retrofit instance builder
 */
object GeocodingRetrofit{

    /**
     * @return the created retrofit instance
     * @param context context
     */
    fun getGeocodingRetrofitInstance(context: Context) = Retrofit.Builder()
        .baseUrl("https://geocoding-api.open-meteo.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}