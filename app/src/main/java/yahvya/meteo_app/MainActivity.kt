package yahvya.meteo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import yahvya.meteo_app.navigation.NavigationGraph
import yahvya.meteo_app.ui.theme.MeteoappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MeteoappTheme {
                val navController = rememberNavController()

                NavigationGraph(
                    navController= navController
                )
            }
        }
    }
}