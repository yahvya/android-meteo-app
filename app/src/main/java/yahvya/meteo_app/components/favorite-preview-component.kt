package yahvya.meteo_app.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yahvya.meteo_app.dtos.WeatherDto

/**
 * @brief favorite preview component
 * @param modifier modifier
 * @param weatherDto weather data
 * @param isFavorite state to update of favorite state pushed
 */
@Composable
fun FavoritePreviewComponent(
    modifier: Modifier,
    weatherDto: WeatherDto,
    isFavorite: MutableState<Boolean>,
    onButtonClicked: () -> Unit
){
    Row(
        modifier= modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text= weatherDto.placeName,
            modifier = Modifier.weight(weight= 1f, fill = false),
            lineHeight = 35.sp
        )
        Row {
            IconButton(onClick = onButtonClicked) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = "Informations")
            }
            IconButton(onClick = {isFavorite.value = !isFavorite.value}) {
                if(isFavorite.value)
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorite state",
                        tint= Color.Red
                    )
                else
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorite state"
                    )
            }
        }
    }
}

@Composable
@Preview
fun FavoritePreviewComponentPreview(){
    val favoriteSelectionState = remember {mutableStateOf(true)}

    FavoritePreviewComponent(
        modifier = Modifier.fillMaxWidth(),
        weatherDto = WeatherDto(
            placeName = "Corte",
            longitude = "-122.083922",
            latitude = "37.4220936",
            windSpeedUnit = "Km/h",
            temperatureUnit = "°C",
            temperatureMeasures = mutableListOf()
        ),
        isFavorite = favoriteSelectionState,
        onButtonClicked = {}
    )
}