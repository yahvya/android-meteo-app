package yahvya.meteo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import yahvya.meteo_app.navigation.ApplicationNavigationBar
import yahvya.meteo_app.navigation.NavigationGraph
import yahvya.meteo_app.ui.theme.MeteoappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MeteoappTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        ApplicationNavigationBar(navController= navController)
                    },
                    content = {
                        NavigationGraph(
                            navController= navController,
                            modifier = Modifier.padding(it)
                        )
                    }
                )
            }
        }
    }
}