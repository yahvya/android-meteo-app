package yahvya.meteo_app.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yahvya.meteo_app.dtos.WeatherDto

/**
 * @brief weather preview component
 * @param modifier modifier
 * @param weatherDto weather dto
 * @param onViewClicked on information row clicked
 */
@Composable
fun WeatherPreviewComponent(
    modifier: Modifier,
    weatherDto: WeatherDto,
    onViewClicked: () -> Unit
){
    Row(
        modifier= modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
            .padding(5.dp)
            .clickable {
                onViewClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        
    ) {
        Text(
            text= weatherDto.placeName,
            modifier = Modifier.weight(weight= 1f, fill = false),
            lineHeight = 35.sp
        )
        Icon(imageVector = Icons.Filled.Info, contentDescription = "Informations")
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
            windSpeedUnit = "Km/h",
            temperatureUnit = "°C",
            temperatureMeasures = mutableListOf()
        ),
        onViewClicked = {}
    )
}