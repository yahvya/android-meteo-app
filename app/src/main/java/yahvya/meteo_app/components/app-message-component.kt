package yahvya.meteo_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * @brief application message shower
 * @param messageState message updatable state by the component
 * @param onDismiss on dismiss action
 */
@Composable
fun AppMessageComponent(messageState: MutableState<String?>,onDismiss: () -> Unit = {}){
    if(messageState.value !== null)
        Dialog(onDismissRequest = {
            messageState.value = null
            onDismiss()
        }) {
            Box(modifier = Modifier
                .size(width= 300.dp,height= 200.dp)
                .background(color= MaterialTheme.colorScheme.surface,shape = RoundedCornerShape(16.dp))
                .padding(all= 16.dp)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text= messageState.value!!)
                    Spacer(modifier = Modifier.height(height= 25.dp))
                    Button(onClick = {
                        messageState.value = null
                        onDismiss()
                    }) {
                        Text(text= "Ok")
                    }
                }
            }
        }
}