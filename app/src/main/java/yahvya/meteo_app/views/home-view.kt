package yahvya.meteo_app.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import yahvya.meteo_app.components.FavoritePreviewComponent
import yahvya.meteo_app.components.GetLocationComponent
import yahvya.meteo_app.components.WeatherPreviewComponent
import yahvya.meteo_app.dtos.HourlyWeatherData

/**
 * @brief home page
 * @param modifier modifier
 * @param lookAllFavoritesClick event on click to see all favorites
 */
@Composable
fun HomeView(
    modifier:Modifier,
    lookAllFavoritesClick: (Int) -> Unit
){
    val research = remember { mutableStateOf("") }
    val proposals = remember { mutableStateListOf<WeatherDto>() }
    val favorites = remember { mutableStateListOf<WeatherDto>() }

    proposals.add(
        WeatherDto(
            placeName = "Nom",
            cloudMeasureUnit = "mm",
            windSpeedUnit = "km/h",
            temperatureMeasures = mutableListOf(
                HourlyWeatherData(
                    date = "2024-12-17T07:00",
                    temperatureMax = "90",
                    temperatureMin = "10",
                    temperature = "300",
                    rainMeasure = "20",
                    cloudHighMeasure = "10",
                    cloudLowMeasure = "10"
                )
            ),
            temperatureUnit = "°C",
            latitude = "lat",
            longitude = "lont",
            rainMeasureUnit = "mm"
        )
    );

    Column(
        modifier= modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        // searchbar
        SearchbarComponent(
            modifier= Modifier.fillMaxWidth(),
            textFieldValue = research,
            searchbarPlaceholder = "Rechercher une ville",
            description = "Rechercher une ville",
            onButtonClicked = {}
        )

        GetLocationComponent(
            modifier=Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp),
            onLocationGet = {},
            onDeny = {}
        )

        // search propositions / results
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

        // certain favorites
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
                        isFavorite = favoriteState,
                        onButtonClicked = {}
                    )
                }
            }

            // see more favorites
            ClickableText(
                text= AnnotatedString(text= "Voir plus ..."),
                onClick = lookAllFavoritesClick,
                style = TextStyle(Color(13,110,253),18.sp, textDecoration = TextDecoration.Underline)
            )
        }
    }
}

@Composable
@Preview
fun HomeViewPreview(){
    HomeView(
        modifier = Modifier,
        lookAllFavoritesClick = {}
    )
}