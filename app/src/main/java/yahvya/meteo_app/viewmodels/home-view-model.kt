package yahvya.meteo_app.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * @brief home view model
 */
class HomeViewModel : ViewModel(){
    /**
     * @brief searchbar state
     */
    private val researchState: MutableState<String> = mutableStateOf(value= "")

    /**
     * @return research state
     */
    fun getResearchState(): MutableState<String> = this.researchState
}