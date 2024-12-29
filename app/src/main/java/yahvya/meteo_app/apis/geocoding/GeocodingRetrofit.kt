package yahvya.meteo_app.apis.geocoding

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import yahvya.meteo_app.apis.CacheConfig

/**
 * @brief retrofit instance builder
 */
object GeocodingRetrofit{

    /**
     * @return the created retrofit instance
     * @param context context
     * @return retrofit instance
     */
    fun getGeocodingRetrofitInstance(context: Context):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(CacheConfig.buildHttpClient(context = context, apiName = "geocoding"))
            .build()
    }
}