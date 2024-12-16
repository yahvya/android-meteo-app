package yahvya.meteo_app.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yahvya.meteo_app.dtos.HourlyWeatherData
import yahvya.meteo_app.dtos.WeatherDto

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
       Text("${key} : ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
       Text(value)
    }
}

/**
 * @brief meteao details page
 * @param modifier modifier
 * @param weatherDto weather data
 * @param onAddInFavorites event when add in favorites is clicked
 */
@Composable
fun MeteoDetailsView(
    modifier: Modifier,
    weatherDto: WeatherDto,
    onAddInFavorites: () -> Unit
){
    var expanded = remember { mutableStateOf(false) }
    var optionsMap = mutableMapOf<String,HourlyWeatherData>()
    val hourlyDataToShow = remember {mutableStateOf<HourlyWeatherData?>(null)}

    // create a map indexed by the date and as value the hourly data
    weatherDto.temperatureMeasures.forEach{
        optionsMap[it.date] = it
    }

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
                fontWeight = FontWeight.Bold
            )
            Button(onClick = onAddInFavorites) {
                Text("Ajouter aux favoris")
            }
        }

        // choose time menu
        Column {
            ClickableText(
                text=AnnotatedString("Choisir l'heure"),
                onClick = {expanded.value = !expanded.value },
                style = TextStyle(fontSize = 20.sp, fontStyle = FontStyle.Italic)
            )
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                optionsMap.forEach{
                    DropdownMenuItem(
                        text = { Text(it.key) },
                        onClick = {
                            expanded.value = false
                            hourlyDataToShow.value = it.value
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

@Composable
@Preview
fun MeteoDetailsViewPreview(){
    MeteoDetailsView(
        modifier = Modifier,
        weatherDto = WeatherDto(
            placeName = "Corte",
            longitude = "-122.083922",
            latitude = "37.4220936",
            cloudMeasureUnit = "%",
            windSpeedUnit = "Km/h",
            rainMeasureUnit = "mm",
            temperatureUnit = "°C",
            temperatureMeasures = mutableListOf(
                HourlyWeatherData(
                    date = "2024-02-17",
                    temperatureMax = "100",
                    temperatureMin = "0",
                    temperature = "30",
                    rainMeasure = "30",
                    cloudHighMeasure = "30",
                    cloudLowMeasure = "20"
                ),
                HourlyWeatherData(
                    date = "2024-02-18",
                    temperatureMax = "100",
                    temperatureMin = "0",
                    temperature = "30",
                    rainMeasure = "30",
                    cloudHighMeasure = "30",
                    cloudLowMeasure = "20"
                )
            )
        ),
        onAddInFavorites = {}
    )
}