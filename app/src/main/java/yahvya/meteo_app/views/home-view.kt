package yahvya.meteo_app.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yahvya.meteo_app.components.SearchbarComponent
import yahvya.meteo_app.dtos.WeatherDto
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import yahvya.meteo_app.components.FavoritePreviewComponent
import yahvya.meteo_app.components.WeatherPreviewComponent

@Composable
fun HomeView(){
    val research = remember { mutableStateOf("") }
    val proposals = remember { mutableStateListOf<WeatherDto>() }
    val favorites = remember { mutableStateListOf<WeatherDto>() }

    proposals.add(WeatherDto(
        placeName = "Corte",
        longitude = "-122.083922",
        latitude = "37.4220936",
        cloudMeasureUnit = "%",
        windSpeedUnit = "Km/h",
        rainMeasureUnit = "mm",
        temperatureUnit = "°C",
        temperatureMeasures = mutableListOf()
    ))

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
        modifier= Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        SearchbarComponent(
            modifier= Modifier.fillMaxWidth(),
            textFieldValue = research,
            searchbarPlaceholder = "Rechercher une ville",
            description = "Rechercher une ville",
            onButtonClicked = {}
        )

        if(proposals.isNotEmpty()){
            Text(
                text= "Résultats",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items= proposals){ item ->
                    WeatherPreviewComponent(
                        modifier = Modifier.fillMaxWidth(),
                        weatherDto = item,
                        onButtonClicked = {}
                    )
                }
            }
        }

        if(favorites.isNotEmpty()){
            Text(
                text= "Vos favoris",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items= favorites){ item ->
                    val favoriteState = remember { mutableStateOf(true) }
                    FavoritePreviewComponent(
                        modifier = Modifier.fillMaxWidth(),
                        weatherDto = item,
                        isFavorite = favoriteState
                    )
                }
            }

            ClickableText(
                text= AnnotatedString(text= "Voir plus ..."),
                onClick = {},
                style = TextStyle(Color(13,110,253),18.sp, textDecoration = TextDecoration.Underline)
            )
        }
    }
}

@Composable
@Preview
fun HomeViewPreview(){
    HomeView()
}