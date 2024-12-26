package yahvya.meteo_app.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener

/**
 * @brief component to get user location
 * @param modifier modifier
 * @param context location context
 * @param onLocationGet on location get callback
 * @param onDeny on location permission deny callback
 */
@Composable
fun GetLocationComponent(
    modifier: Modifier,
    onLocationGet:(Location?) -> Unit,
    onDeny: () -> Unit,
    context: Context = LocalContext.current,
){
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted)
            getLastKnownLocation(fusedLocationClient) { location -> onLocationGet(location)}
        else
            onDeny()
    }

    // internal function to ask permission
    fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            getLastKnownLocation(fusedLocationClient) {location -> onLocationGet(location)}
        else
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Button(modifier = modifier, onClick = {requestLocationPermission()}) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text= "Me localiser", fontSize = 17.sp)
            Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Location")
        }
    }
}

/**
 * @brief provide the last known location
 * @param fusedLocationClient location provider
 * @param onLocationReceived location received callback
 */
@SuppressLint("MissingPermission")
private fun getLastKnownLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationReceived: (Location?) -> Unit
) {
    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        object: CancellationToken(){
            override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken = this

            override fun isCancellationRequested(): Boolean = false
        }
    ).addOnSuccessListener { onLocationReceived(it) }
}

@Composable
@Preview
fun GetLocationComponentPreview(){
    GetLocationComponent(
        modifier = Modifier,
        onLocationGet = {location ->
            Log.d("info","Longitude : ${location?.longitude} - Latitude : ${location?.latitude}")
        },
        onDeny = {
            Log.d("info","Permission refusée")
        }
    )
}