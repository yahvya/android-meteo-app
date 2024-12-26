package yahvya.meteo_app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

/**
 * @brief application navigation bar
 * @param navController navigation controller
 */
@Composable
fun ApplicationNavigationBar(navController: NavController){
    NavigationBar {
        NavigationBarItem(
            selected = navController.currentDestination?.route == Routes.HOME_PAGE,
            onClick = { navController.navigate(route = Routes.HOME_PAGE) },
            label = { Text(text = "Accueil") },
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "") }
        )

        NavigationBarItem(
            selected = navController.currentDestination?.route == Routes.FAVORITES,
            onClick = { navController.navigate(route = Routes.FAVORITES) },
            label = { Text(text = "Favoris") },
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "") }
        )
    }
}