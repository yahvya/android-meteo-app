package yahvya.meteo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import yahvya.meteo_app.navigation.ApplicationNavigationBar
import yahvya.meteo_app.navigation.NavigationGraph
import yahvya.meteo_app.ui.theme.MeteoappTheme
import yahvya.meteo_app.viewmodels.MainModel

class MainActivity : ComponentActivity() {
    /**
     * @brief main model
     */
    private val mainModel by viewModels<MainModel>()

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
                            mainModel= this.mainModel,
                            modifier = Modifier.padding(it)
                        )
                    }
                )
            }
        }
    }
}