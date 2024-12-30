package yahvya.meteo_app.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import yahvya.meteo_app.components.FavoritePreviewComponent
import yahvya.meteo_app.viewmodels.FavoritesViewModel
import yahvya.meteo_app.viewmodels.WeatherDetailsViewModel

/**
 * @brief favorites page
 * @param modifier modifier
 * @param onWeatherPreviewClick action on details click
 * @param weatherDetailsViewModel weather details view model
 * @param favoritesViewModel page view model
 */
@Composable
fun FavoritesView(
    modifier:Modifier,
    onWeatherPreviewClick: () -> Unit,
    weatherDetailsViewModel: WeatherDetailsViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel()
){
    val favorites = favoritesViewModel.getFavoritesState()

    LazyColumn(
        modifier= modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        item{
            Text(
                text= "Vos favoris".uppercase(),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if(favorites.isNotEmpty()){
            // favorites list
            items(items= favorites){ item ->
                val isSelected = remember{ mutableStateOf(value= true) }

                LaunchedEffect(isSelected.value) {
                    if(!isSelected.value)
                        favoritesViewModel.removeFavorite(item)
                }

                FavoritePreviewComponent(
                    modifier = Modifier.fillMaxWidth(),
                    weatherDto = item,
                    isFavorite = isSelected,
                    onViewClicked = {
                        weatherDetailsViewModel.weatherDto.value = item
                        onWeatherPreviewClick()
                    }
                )
            }
        }
        else{
            item{
                Text(text= "Vous n'avez pas de favoris enregistré", fontSize = 18.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
@Preview
fun FavoritesViewPreview(){
    FavoritesView(modifier = Modifier, onWeatherPreviewClick = {})
}