package yahvya.meteo_app.apis.geocoding

/**
 * @brief city data contract
 */
data class CityDto(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val country: String,
    val admin1: String?,
    val admin2: String?,
    val admin3: String?
){
    /**
     * @return displayable string to represent the place
     */
    fun toDisplay():String = listOfNotNull(this.name, this.admin2, this.admin3, this.country).joinToString(separator = " ")
}

/**
 * @brief result dto
 */
data class CityResultDto(
    val results: List<CityDto>
)