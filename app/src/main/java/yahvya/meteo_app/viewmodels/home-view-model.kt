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
import yahvya.meteo_app.apis.openweather.OpenWeatherRetrofit
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

    /**
     * @brief search proposals from research string
     * @param search research
     */
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
                // find cities
                val cities = GeocodingRetrofit.INSTANCE.getCities(placeName = search)?.results

                if(cities !== null){
                    proposals.value = cities.mapNotNull { cityDto ->
                        // find weather from city location
                        val result = OpenWeatherRetrofit.INSTANCE.getWeatherOf(
                            longitude = cityDto.longitude,
                            latitude = cityDto.latitude
                        )

                        if (result !== null) WeatherDto.fromOpenWeatherDto(openWeatherDto = result,placeDisplayName = cityDto.toDisplay()) else null
                    }
                }
                else
                    proposals.value = listOf()
            }
            catch(e:Exception){
                Log.d("Recherche erreur",e.toString())
            }
        }
    }

    /**
     * @brief search weather data from location
     * @param longitude longitude
     * @param latitude latitude
     */
    fun searchFromLocation(longitude: Double,latitude:Double){
        try{
            this.citiesSearchJob?.cancel()
            this.citiesSearchJob = viewModelScope.launch {
                var openWeatherDto = OpenWeatherRetrofit.INSTANCE.getWeatherOf(longitude = longitude,latitude= latitude)

                if(openWeatherDto != null)
                    proposals.value = listOf(WeatherDto.fromOpenWeatherDto(openWeatherDto = openWeatherDto))
                else
                    Log.d("Recherche location","Valeur null")
            }
        }
        catch(e:Exception){
            Log.d("Recherche erreur location",e.toString())
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