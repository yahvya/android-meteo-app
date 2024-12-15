package yahvya.meteo_app.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * @brief Searchbar component
 * @param modifier modifier
 * @param textFieldValue text field content state
 * @param searchbarPlaceholder default placeholder
 * @param description button description
 * @param onButtonClicked button click callback
 * @param onValueChange text field value change callback
 */
@Composable
fun SearchbarComponent(
    modifier: Modifier,
    textFieldValue: MutableState<String>,
    searchbarPlaceholder: String,
    description: String,
    onButtonClicked: () -> Unit,
    onValueChange: (String) -> Unit
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = textFieldValue.value,
            onValueChange = onValueChange,
            placeholder = { Text(searchbarPlaceholder) }
        )

        IconButton(onClick = onButtonClicked) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = description
            )
        }
    }
}

@Composable
@Preview
fun SearchbarComponentPreview(){
    val citySearchState = remember { mutableStateOf("") }

    SearchbarComponent(
        modifier = Modifier.fillMaxSize(),
        textFieldValue = citySearchState,
        searchbarPlaceholder= "Entrez le nom de la ville",
        description = "Rechercher la météo",
        onButtonClicked = {
            Log.d("Info","Ce que la personne a écrit : ${citySearchState.value}")
        },
        onValueChange = {
            newValue -> citySearchState.value = newValue
        }
    )
}