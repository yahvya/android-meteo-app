package yahvya.meteo_app.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    searchbarPlaceholder: String,
    description: String,
    onButtonClicked: () -> Unit
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textFieldValue.value,
            onValueChange = {textFieldValue.value = it},
            placeholder = { Text(searchbarPlaceholder) }
        )

        IconButton(
            onClick = onButtonClicked,
            modifier = Modifier.size(70.dp)
        ) {
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
        }
    )
}