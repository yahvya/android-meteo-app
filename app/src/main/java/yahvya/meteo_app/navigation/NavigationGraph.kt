package yahvya.meteo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import yahvya.meteo_app.viewmodels.HomeViewModel
import yahvya.meteo_app.viewmodels.WeatherDetailsViewModel
import yahvya.meteo_app.views.FavoritesView
import yahvya.meteo_app.views.HomeView
import yahvya.meteo_app.views.WeatherDetailsView

/**
 * @brief application routes
 */
data object Routes{
    const val HOME_PAGE = "home"
    const val FAVORITES = "favorites"
    const val WEATHER_DETAILS = "details"
}

/**
 * @brief application navigation graph
 * @param navController navigation host controller
 * @param modifier modifier
 */
@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    val weatherDetailsViewModel: WeatherDetailsViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    NavHost(
        navController= navController,
        startDestination = Routes.HOME_PAGE,
        modifier = modifier
    ) {
        composable(route= Routes.HOME_PAGE) {
            HomeView(
                modifier = Modifier,
                lookAllFavoritesClick = {
                    navController.navigate(route= Routes.FAVORITES)
                },
                onWeatherPreviewClick = {
                    navController.navigate(route= Routes.WEATHER_DETAILS)
                },
                weatherDetailsViewModel = weatherDetailsViewModel,
                homeViewModel = homeViewModel
            )
        }

        composable(route= Routes.FAVORITES) {
            FavoritesView(
                modifier = Modifier,
                onWeatherPreviewClick = {
                    navController.navigate(route= Routes.WEATHER_DETAILS)
                },
                weatherDetailsViewModel = weatherDetailsViewModel,
            )
        }

        composable(route= Routes.WEATHER_DETAILS){
            WeatherDetailsView(
                modifier = Modifier,
                onAddInFavorites = {},
                onBackToPrevious = {navController.popBackStack()},
                weatherDetailsViewModel = weatherDetailsViewModel
            )
        }
    }
}