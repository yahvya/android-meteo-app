package yahvya.meteo_app.apis.openweather

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @brief open weather retrofit instance
 */
object OpenWeatherRetrofit{
    /**
     * @brief retrofit instance
     * @param context context
     */
    fun getOpenWeatherRetrofitInstance(context: Context) = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}