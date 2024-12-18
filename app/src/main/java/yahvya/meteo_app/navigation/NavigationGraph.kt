package yahvya.meteo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import yahvya.meteo_app.dtos.WeatherDto
import yahvya.meteo_app.views.FavoritesView
import yahvya.meteo_app.views.HomeView
import yahvya.meteo_app.views.MeteoDetailsView

/**
 * @brief application routes
 */
data object Routes{
    const val HOME_PAGE = "home"
    const val FAVORITES = "favorites"
    const val METEO_DETAILS = "details"
}

/**
 * @brief application navigation graph
 * @param navController navigation host controller
 * @param modifier modifier
 */
@Composable
fun NavigationGraph(navController: NavHostController,modifier: Modifier = Modifier){
    lateinit var weatherDto: WeatherDto

    NavHost(
        navController= navController,
        startDestination = Routes.HOME_PAGE,
        modifier = modifier
    ) {
        composable(route= Routes.HOME_PAGE) {
            HomeView(modifier = Modifier, lookAllFavoritesClick = {
                navController.navigate(route= Routes.FAVORITES)
            })
        }

        composable(route= Routes.FAVORITES) {
            FavoritesView(modifier = Modifier)
        }

        composable(route= Routes.METEO_DETAILS){
            MeteoDetailsView(modifier = Modifier, weatherDto = weatherDto, onAddInFavorites = {})
        }
    }
}