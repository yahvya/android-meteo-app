package yahvya.meteo_app.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yahvya.meteo_app.MainActivity
import yahvya.meteo_app.apis.geocoding.GeocodingRequests
import yahvya.meteo_app.apis.geocoding.GeocodingRetrofit
import yahvya.meteo_app.apis.openweather.OpenWeatherRequests
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
    private val proposalsState:MutableState<List<WeatherDto>> = mutableStateOf(listOf())

    /**
     * @brief favorites
     */
    private val favoritesState = mutableStateListOf<WeatherDto>()

    /**
     * @brief message to display to the user
     */
    private val userMessageState: MutableState<String?> = mutableStateOf(null)

    /**
     * @brief cities search coroutine
     */
    private var citiesSearchJob: Job? = null

    /**
     * @brief geocoding api request maker
     */
    private var geocodingRequestsMaker: GeocodingRequests? = null

    /**
     * @brief open weather api requests maker
     */
    private var openWeatherRequestsMaker: OpenWeatherRequests? = null

    companion object{
        /**
         * @brief user typing delay to wait before launching requests
         */
        const val USER_TYPING_DELAY = 400L
    }

    /**
     * @brief load some favorites
     */
    fun loadFavorites(){
        viewModelScope.launch {
            favoritesState.clear()
            favoritesState.addAll(MainActivity.database.favoritesDao()
                .getSomeones(countOfItems = 4)
                .map { WeatherDto.fromDatabaseEntity(favoritesEntity = it) })
        }
    }

    fun removeInFavorites(weatherDto: WeatherDto){
        val id = weatherDto.getEntityId()

        if(id !== null){
            viewModelScope.launch {
                MainActivity.database
                    .favoritesDao()
                    .delete(id= id)
                favoritesState.remove(weatherDto)
            }
        }
    }

    /**
     * @brief search proposals from research string
     * @param search research
     * @param context context
     */
    fun searchProposals(search: String,context: Context){
        try{
            if(this.geocodingRequestsMaker === null)
                this.geocodingRequestsMaker = GeocodingRetrofit.getGeocodingRetrofitInstance(context= context).create(GeocodingRequests::class.java)

            if(this.openWeatherRequestsMaker === null)
                this.openWeatherRequestsMaker = OpenWeatherRetrofit.getOpenWeatherRetrofitInstance(context = context).create(OpenWeatherRequests::class.java)

            if(search.isBlank()){
                proposalsState.value = listOf()
                return
            }

            this.citiesSearchJob?.cancel()
            this.citiesSearchJob = viewModelScope.launch {
                // delay for user typing
                delay(timeMillis = USER_TYPING_DELAY)

                try{
                    // find cities
                    val cities = geocodingRequestsMaker!!.getCities(placeName = search)?.results

                    if(cities !== null){
                        proposalsState.value = cities.mapNotNull { cityDto ->
                            // find weather from city location
                            val result = openWeatherRequestsMaker!!.getWeatherOf(
                                longitude = cityDto.longitude,
                                latitude = cityDto.latitude
                            )

                            if (result !== null) WeatherDto.fromOpenWeatherDto(openWeatherDto = result,placeDisplayName = cityDto.toDisplay()) else null
                        }
                    }
                    else{
                        proposalsState.value = listOf()
                        userMessageState.value = "Aucune proposition disponible la ville <${search}> :("
                    }
                }
                catch(_:Exception){
                    userMessageState.value = "Aucun résultat trouvé, pensez à vérifier l'état de la connexion :("
                }
            }
        }
        catch (_:Exception){
            userMessageState.value = "Erreur technique, pensez à relancer l'application :("
        }
    }

    /**
     * @brief search weather data from location
     * @param longitude longitude
     * @param latitude latitude
     * @param context context
     */
    fun searchFromLocation(longitude: Double,latitude:Double,context: Context){
        try{
            if(this.openWeatherRequestsMaker === null)
                this.openWeatherRequestsMaker = OpenWeatherRetrofit.getOpenWeatherRetrofitInstance(context = context).create(OpenWeatherRequests::class.java)

            this.citiesSearchJob?.cancel()
            this.citiesSearchJob = viewModelScope.launch {
                try{
                    val openWeatherDto = openWeatherRequestsMaker!!.getWeatherOf(longitude = longitude,latitude= latitude)

                    if(openWeatherDto != null)
                        proposalsState.value = listOf(WeatherDto.fromOpenWeatherDto(openWeatherDto = openWeatherDto))
                    else
                        userMessageState.value = "Echec de récupération de la méteo de cette zone :("
                }
                catch(_:Exception){
                    userMessageState.value = "Echec de récupération de la méteo de cette zone, pensez à vérifier l'état de la connexion :("
                }
            }
        }
        catch(_:Exception){
            userMessageState.value = "Erreur technique, pensez à relancer l'application  :("
        }
    }

    /**
     * @return research state
     */
    fun getResearchState(): MutableState<String> = this.researchState

    /**
     * @return proposals state
     */
    fun getProposalsState():MutableState<List<WeatherDto>> = this.proposalsState

    /**
     * @return user message state
     */
    fun getUserMessageState():MutableState<String?> = this.userMessageState

    /**
     * @return favorites state
     */
    fun getFavoritesState() = this.favoritesState
}