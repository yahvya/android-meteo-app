package yahvya.meteo_app.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yahvya.meteo_app.dtos.WeatherDto
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import yahvya.meteo_app.components.FavoritePreviewComponent

/**
 * @brief favorites page
 * @param modifier modifier
 */
@Composable
fun FavoritesView(modifier:Modifier){
    val favorites = remember { mutableStateListOf<WeatherDto>() }

    favorites.add(WeatherDto(
        placeName = "Corte",
        longitude = "-122.083922",
        latitude = "37.4220936",
        cloudMeasureUnit = "%",
        windSpeedUnit = "Km/h",
        rainMeasureUnit = "mm",
        temperatureUnit = "°C",
        temperatureMeasures = mutableListOf()
    ))

    Column(
        modifier= modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        Text(
            text= "Vos favoris",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        if(favorites.isNotEmpty()){
            // favorites list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items= favorites){ item ->
                    val isSelected = remember{ mutableStateOf(true) }

                    FavoritePreviewComponent(
                        modifier = Modifier.fillMaxWidth(),
                        weatherDto = item,
                        isFavorite = isSelected,
                        onButtonClicked = {}
                    )
                }
            }
        }
        else{
            Text("Vous n'avez pas de favoris enregistré", fontSize = 18.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
@Preview
fun FavoritesViewPreview(){
    FavoritesView(modifier = Modifier)
}