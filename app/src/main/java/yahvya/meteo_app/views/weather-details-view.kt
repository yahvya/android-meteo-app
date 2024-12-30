package yahvya.meteo_app.views

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import yahvya.meteo_app.components.AppMessageComponent
import yahvya.meteo_app.dtos.TimeWeatherData
import yahvya.meteo_app.dtos.WeatherDto
import yahvya.meteo_app.viewmodels.WeatherDetailsViewModel
import java.time.LocalDate

/**
 * @brief text with border component
 * @param key text to put in front
 * @param value linked value
 */
@Composable
fun BoxedText(key:String,value:String){
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier= Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
            .padding(5.dp)
    ) {
       Text("$key : ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
       Text(value)
    }
}

/**
 * @brief meteao details page
 * @param modifier modifier
 * @param onBackToPrevious go back to previous page
 * @param weatherDetailsViewModel page model
 * @param context context
 */
@Composable
fun WeatherDetailsView(
    modifier: Modifier,
    onBackToPrevious:() -> Unit,
    weatherDetailsViewModel: WeatherDetailsViewModel = viewModel(),
    context:Context = LocalContext.current
){
    val expanded = remember { mutableStateOf(false) }
    val optionsMap = mutableMapOf<String,TimeWeatherData>()
    val weatherDto = weatherDetailsViewModel.weatherDto.value

    weatherDetailsViewModel.loadActual(context = context)

    if(weatherDto == null){
        val errorMessageState = remember { mutableStateOf<String?>("Vous n'avez pas accès à cette page ;)") }

        AppMessageComponent(
            messageState = errorMessageState,
            onDismiss = { onBackToPrevious() }
        )
    }
    else{
        // create a map indexed by the date and as value the hourly data
        weatherDto.temperatureMeasures.forEach{
            optionsMap[it.time] = it
        }

        var defaultItemToShow:TimeWeatherData? = null

        // show the last hour
        val greaterKey = optionsMap.keys.minWithOrNull(compareBy{LocalDate.parse(it)})

        if(greaterKey !== null)
            defaultItemToShow = optionsMap[greaterKey]

        val dayData = remember {mutableStateOf(defaultItemToShow)}

        Column(
            modifier= modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            // header
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text= weatherDto.placeName,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(weight= 1f, fill = false),
                    lineHeight = 35.sp
                )
                if(!weatherDto.isFavorite)
                    Button(onClick = {weatherDetailsViewModel.addInFavorites()}) {
                        Text("Ajouter aux favoris")
                    }
                else
                    Button(onClick = {weatherDetailsViewModel.deleteInFavorites()}) {
                        Text("Supprimer des favoris")
                    }
            }

            // choose time menu
            Column {
                Text(
                    text= "Choisir l'heure",
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.clickable { expanded.value = !expanded.value }
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    optionsMap.forEach{
                        DropdownMenuItem(
                            text = { Text(it.key) },
                            onClick = {
                                dayData.value = it.value
                                expanded.value = false
                            }
                        )
                    }
                }
            }

            // print meteo data
            if(dayData.value !== null){
                // show the select hour data
                val toShow = dayData.value!!

                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    BoxedText(key= "Date", value = toShow.time)
                    BoxedText(key="Temps",value= WeatherDto.findDescriptionFromWeatherCode(code= toShow.weatherCode))
                    BoxedText(key= "Temperature", value = "${toShow.temperature}${weatherDto.temperatureUnit}")
                    BoxedText(key= "Temperature minimale", value = "${toShow.temperatureMin}${weatherDto.temperatureUnit}")
                    BoxedText(key= "Temperature maximale", value = "${toShow.temperatureMax}${weatherDto.temperatureUnit}")
                    BoxedText(key= "Vitesse du vent", value = "${toShow.windSpeed}${weatherDto.windSpeedUnit}")
                    BoxedText(key= "Longitude", value = weatherDto.longitude)
                    BoxedText(key= "Latitude", value = weatherDto.latitude)
                }
            }
        }
    }
}

@Composable
@Preview
fun WeatherDetailsViewPreview(weatherDetailsViewModel: WeatherDetailsViewModel = viewModel()){
    weatherDetailsViewModel.weatherDto.value = WeatherDto(
        placeName = "Corte",
        longitude = "-122.083922",
        latitude = "37.4220936",
        windSpeedUnit = "Km/h",
        temperatureUnit = "°C",
        temperatureMeasures = mutableListOf()
    )

    WeatherDetailsView(
        modifier = Modifier,
        onBackToPrevious = {}
    )
}