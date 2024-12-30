package yahvya.meteo_app.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import yahvya.meteo_app.MainActivity
import yahvya.meteo_app.dtos.WeatherDto

/**
 * @brief favorites view model
 */
class FavoritesViewModel : ViewModel() {
    private val favoritesState = mutableStateListOf<WeatherDto>()

    init{
        loadFavorites()
    }

    /**
     * @brief load all favorites
     */
    fun loadFavorites(){
        viewModelScope.launch {
            // allow user to move his finger
            favoritesState.clear()
            favoritesState.addAll(MainActivity.database
                .favoritesDao()
                .getAll()
                .map { WeatherDto.fromDatabaseEntity(favoritesEntity = it) })
        }
    }

    /**
     * @brief remove to favorites
     * @param weatherDto dto
     */
    fun removeFavorite(weatherDto: WeatherDto){
        val id = weatherDto.getEntityId()

        if(id !== null){
            viewModelScope.launch {
                MainActivity.database
                    .favoritesDao()
                    .delete(id= id)

                // during my tests the remove was too quick and the user click on the next element
                favoritesState.remove(weatherDto)
            }
        }
    }

    /**
     * @return favorites states
     */
    fun getFavoritesState() = favoritesState
}