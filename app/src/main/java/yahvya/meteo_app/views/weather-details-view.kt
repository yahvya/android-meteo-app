package yahvya.meteo_app.views

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import yahvya.meteo_app.dtos.HourlyWeatherData
import yahvya.meteo_app.dtos.WeatherDto
import yahvya.meteo_app.viewmodels.WeatherDetailsViewModel
import java.time.LocalDateTime

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
 * @param onAddInFavorites event when add in favorites is clicked
 * @param weatherDetailsViewModel page model
 */
@Composable
fun WeatherDetailsView(
    modifier: Modifier,
    onAddInFavorites: () -> Unit,
    weatherDetailsViewModel: WeatherDetailsViewModel = viewModel()
){
    val expanded = remember { mutableStateOf(false) }
    val optionsMap = mutableMapOf<String,HourlyWeatherData>()

    val weatherDto = weatherDetailsViewModel.weatherDto

    if(weatherDto == null){

    }
    else{
        // create a map indexed by the date and as value the hourly data
        weatherDto.temperatureMeasures.forEach{
            optionsMap[it.date] = it
        }

        var defaultItemToShow:HourlyWeatherData? = null

        // show the last hour
        val greaterKey = optionsMap.keys.maxWithOrNull(compareBy{LocalDateTime.parse(it)})

        if(greaterKey !== null)
            defaultItemToShow = optionsMap[greaterKey]

        val hourlyDataToShow = remember {mutableStateOf<HourlyWeatherData?>(defaultItemToShow)}

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
                Button(onClick = onAddInFavorites) {
                    Text("Ajouter aux favoris")
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
                                hourlyDataToShow.value = it.value
                                expanded.value = false
                            }
                        )
                    }
                }
            }

            // print meteo data
            if(hourlyDataToShow.value !== null){
                // show the select hour data
                val hourlyWeatherData = hourlyDataToShow.value!!

                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    BoxedText(key= "Date", value = hourlyWeatherData.date)
                    BoxedText(key= "Temperature", value = "${hourlyWeatherData.temperature}${weatherDto.temperatureUnit}")
                    BoxedText(key= "Temperature minimale", value = "${hourlyWeatherData.temperatureMin}${weatherDto.temperatureUnit}")
                    BoxedText(key= "Temperature maximale", value = "${hourlyWeatherData.temperatureMax}${weatherDto.temperatureUnit}")
                    BoxedText(key= "Temperature maximale", value = "${hourlyWeatherData.temperatureMax}${weatherDto.temperatureUnit}")
                }
            }
        }
    }
}

@Composable
@Preview
fun WeatherDetailsViewPreview(weatherDetailsViewModel: WeatherDetailsViewModel = viewModel()){
    weatherDetailsViewModel.weatherDto = WeatherDto(
        placeName = "Corte",
        longitude = "-122.083922",
        latitude = "37.4220936",
        cloudMeasureUnit = "%",
        windSpeedUnit = "Km/h",
        rainMeasureUnit = "mm",
        temperatureUnit = "°C",
        temperatureMeasures = mutableListOf(
            HourlyWeatherData(
                date = "2024-12-17T06:00",
                temperatureMax = "100",
                temperatureMin = "0",
                temperature = "30",
                rainMeasure = "30",
                cloudHighMeasure = "30",
                cloudLowMeasure = "20"
            ),
            HourlyWeatherData(
                date = "2024-12-17T07:00",
                temperatureMax = "90",
                temperatureMin = "10",
                temperature = "300",
                rainMeasure = "20",
                cloudHighMeasure = "10",
                cloudLowMeasure = "10"
            )
        )
    )

    WeatherDetailsView(
        modifier = Modifier,
        onAddInFavorites = {}
    )
}