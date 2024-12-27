package yahvya.meteo_app.apis.openweather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @brief open weather retrofit instance
 */
data object OpenWeatherRetrofit {
    val INSTANCE by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherRequests::class.java)
    }
}