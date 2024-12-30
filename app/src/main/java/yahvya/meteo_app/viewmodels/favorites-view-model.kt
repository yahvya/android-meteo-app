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
            favoritesState.clear()
            favoritesState.addAll(MainActivity.database
                .favoritesDao()
                .getAll()
                .map { WeatherDto.fromDatabaseEntity(favoritesEntity = it) })
        }
    }

    /**
     * @return favorites states
     */
    fun getFavoritesState() = favoritesState
}