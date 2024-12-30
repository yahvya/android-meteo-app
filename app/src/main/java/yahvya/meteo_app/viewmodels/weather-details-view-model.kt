package yahvya.meteo_app.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import yahvya.meteo_app.MainActivity
import yahvya.meteo_app.apis.openweather.OpenWeatherRequests
import yahvya.meteo_app.apis.openweather.OpenWeatherRetrofit
import yahvya.meteo_app.dtos.WeatherDto

/**
 * @brief weather details view model
 */
class WeatherDetailsViewModel : ViewModel() {
    /**
     * @brief dto to show the details of
     */
    var weatherDto: MutableState<WeatherDto?> = mutableStateOf(null)

    /**
     * @brief open weather request maker
     */
    private var openWeatherRequestsMaker: OpenWeatherRequests? = null

    /**
     * @brief load the actual data if possible
     * @param context context
     */
    fun loadActual(context: Context){
        try{
            if(openWeatherRequestsMaker === null)
                openWeatherRequestsMaker = OpenWeatherRetrofit.getOpenWeatherRetrofitInstance(context= context).create(OpenWeatherRequests::class.java)

            if(weatherDto.value !== null){
                viewModelScope.launch {
                    try{
                        val result = openWeatherRequestsMaker!!.getWeatherOf(
                            longitude = weatherDto.value!!.longitude.toDouble(),
                            latitude = weatherDto.value!!.latitude.toDouble()
                        )

                        // update the weather dto
                        if(result !== null){
                            val newFormatted = WeatherDto.fromOpenWeatherDto(openWeatherDto = result, placeDisplayName = weatherDto.value!!.placeName)

                            newFormatted.isFavorite = weatherDto.value!!.isFavorite
                            newFormatted.supplementaryData = weatherDto.value!!.supplementaryData

                            weatherDto.value = newFormatted
                        }
                    }
                    catch (_:Exception){}
                }
            }
        }
        catch (_:Exception){}
    }

    /**
     * @brief add in favorites
     */
    fun addInFavorites(){
        if(weatherDto.value !== null){
            viewModelScope.launch {
                val baseEntity = weatherDto.value!!.toDatabaseEntity()
                baseEntity.id = MainActivity.database.favoritesDao().insert(favoritesEntity = baseEntity).toInt()
                weatherDto.value = WeatherDto.fromDatabaseEntity(favoritesEntity = baseEntity)
            }
        }
    }

    /**
     * @brief add in favorites
     */
    fun deleteInFavorites(){
        if(weatherDto.value !== null){
            viewModelScope.launch {
                val id = weatherDto.value!!.getEntityId()
                if(id !== null){
                    MainActivity.database.favoritesDao().delete(id= id)
                    weatherDto.value = weatherDto.value!!.copy(isFavorite = false)
                }
            }
        }
    }
}