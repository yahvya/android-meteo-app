package yahvya.meteo_app.views

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yahvya.meteo_app.components.SearchbarComponent
import yahvya.meteo_app.dtos.WeatherDto
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import yahvya.meteo_app.components.FavoritePreviewComponent
import yahvya.meteo_app.components.GetLocationComponent
import yahvya.meteo_app.components.WeatherPreviewComponent
import yahvya.meteo_app.viewmodels.HomeViewModel
import yahvya.meteo_app.viewmodels.WeatherDetailsViewModel

/**
 * @brief home page
 * @param modifier modifier
 * @param lookAllFavoritesClick event on click to see all favorites
 * @param onWeatherPreviewClick action on details click
 * @param homeViewModel home view model
 * @param weatherDetailsViewModel weather details view model
 */
@Composable
fun HomeView(
    modifier:Modifier,
    lookAllFavoritesClick: () -> Unit,
    onWeatherPreviewClick: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
    weatherDetailsViewModel: WeatherDetailsViewModel = viewModel()
){
    val proposals = remember { mutableStateListOf<WeatherDto>() }
    val favorites = remember { mutableStateListOf<WeatherDto>() }

    Column(
        modifier= modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        Text(
            text = "Ma méteo".uppercase(),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom= 20.dp,top= 5.dp)
        )

        // searchbar
        SearchbarComponent(
            modifier= Modifier.fillMaxWidth(),
            textFieldValue = homeViewModel.getResearchState(),
            searchbarPlaceholder = "Rechercher une ville"
        )

        GetLocationComponent(
            modifier=Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
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
                        onButtonClicked = {
                            weatherDetailsViewModel.weatherDto = item
                            onWeatherPreviewClick()
                        }
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
            Text(
                text= "Voir plus ...",
                modifier= Modifier.clickable {
                    lookAllFavoritesClick()
                },
                color = Color(red=13,green=110,blue=253),
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
@Preview
fun HomeViewPreview(){
    HomeView(
        modifier = Modifier,
        lookAllFavoritesClick = {},
        onWeatherPreviewClick = {}
    )
}