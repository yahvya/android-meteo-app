package yahvya.meteo_app.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GetLocationComponent(
    modifier: Modifier,
    onButtonClicked: () -> Unit
){
    Button(modifier = modifier, onClick = onButtonClicked) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Me localiser")
            Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Location")
        }
    }
}

@Composable
@Preview
fun GetLocationComponentPreview(){
    GetLocationComponent(
        modifier = Modifier,
        onButtonClicked = {
            Log.d("Info","Button clicked")
        }
    )
}