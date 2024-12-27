package yahvya.meteo_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * @brief Searchbar component
 * @param modifier modifier
 * @param textFieldValue text field content state
 * @param searchbarPlaceholder default placeholder
 * @param description button description
 * @param onButtonClicked button click callback
 */
@Composable
fun SearchbarComponent(
    modifier: Modifier,
    textFieldValue: MutableState<String>,
    searchbarPlaceholder: String
){
    OutlinedTextField(
        value = textFieldValue.value,
        onValueChange = {textFieldValue.value = it},
        placeholder = { Text(searchbarPlaceholder) },
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
@Preview
fun SearchbarComponentPreview(){
    val citySearchState = remember { mutableStateOf(value= "") }

    SearchbarComponent(
        modifier = Modifier.fillMaxSize(),
        textFieldValue = citySearchState,
        searchbarPlaceholder= "Entrez le nom de la ville"
    )
}