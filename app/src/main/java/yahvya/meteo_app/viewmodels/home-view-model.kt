package yahvya.meteo_app.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yahvya.meteo_app.apis.geocoding.GeocodingRetrofit
import yahvya.meteo_app.dtos.WeatherDto

/**
 * @brief home view model
 */
class HomeViewModel : ViewModel(){
    /**
     * @brief searchbar state
     */
    private val researchState: MutableState<String> = mutableStateOf(value= "")

    /**
     * @brief results proposals
     */
    private val proposals:MutableState<List<WeatherDto>> = mutableStateOf(listOf())

    /**
     * @brief cities search coroutine
     */
    private var citiesSearchJob: Job? = null

    fun searchProposals(search: String){
        if(search.isBlank()){
            proposals.value = listOf()
            return
        }

        this.citiesSearchJob?.cancel()
        this.citiesSearchJob = viewModelScope.launch {
            // check if user still typing
            delay(timeMillis = 300)

            try{
                val cities = GeocodingRetrofit.INSTANCE.getCities(placeName = search)?.results

                if(cities === null)
                    proposals.value = listOf()
                else
                    proposals.value = cities.map{WeatherDto(
                        placeName = it.toDisplay(),
                        longitude = it.longitude.toString(),
                        latitude = it.latitude.toString(),
                        temperatureUnit = "",
                        windSpeedUnit = "",
                        cloudMeasureUnit = "",
                        rainMeasureUnit = "",
                        temperatureMeasures = mutableListOf(),
                    )}

            }
            catch(e:Exception){
                Log.d("Recherche erreur",e.toString())
            }
        }
    }

    /**
     * @return research state
     */
    fun getResearchState(): MutableState<String> = this.researchState

    /**
     * @return proposals state
     */
    fun getProposalsState():MutableState<List<WeatherDto>> = this.proposals
}