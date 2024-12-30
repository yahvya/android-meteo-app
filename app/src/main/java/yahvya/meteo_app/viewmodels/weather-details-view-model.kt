package yahvya.meteo_app.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import yahvya.meteo_app.MainActivity
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