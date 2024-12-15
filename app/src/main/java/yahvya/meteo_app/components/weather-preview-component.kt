package yahvya.meteo_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import yahvya.meteo_app.dtos.WeatherDto

@Composable
fun WeatherPreviewComponent(
    modifier: Modifier,
    weatherDto: WeatherDto,
    onButtonClicked: () -> Unit
){
    Row(
        modifier= modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(weatherDto.placeName)
        IconButton(onClick = onButtonClicked) {
            Icon(imageVector = Icons.Filled.Info, contentDescription = "Informations")
        }
    }
}

@Composable
@Preview
fun WeatherPreviewComponentPreview(){
    WeatherPreviewComponent(
        modifier = Modifier.fillMaxWidth(),
        weatherDto = WeatherDto(
            placeName = "Corte",
            longitude = "-122.083922",
            latitude = "37.4220936",
            cloudMeasureUnit = "%",
            windSpeedUnit = "Km/h",
            rainMeasureUnit = "mm",
            temperatureUnit = "°C",
            temperatureMeasures = mutableListOf()
        ),
        onButtonClicked = {}
    )
}