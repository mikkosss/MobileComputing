package com.msss.mobilecomputing.ui.home.categoryReminder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.model.LatLng

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderList(
    navController: NavController
) {
    val latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value
    var latitude: Float
    var longitude: Float

    Scaffold {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            TopBar(navController)

            if (latlng == null) {
                latitude = 0.0f
                longitude = 0.0f
                OutlinedButton(
                    onClick = { navController.navigate("map") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Reminder location")
                }
            } else {
                OutlinedButton(
                    onClick = { navController.navigate("map") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Reminder location")
                }
                OutlinedButton(
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(route = "reminderList") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Clear")
                }
                latitude = latlng.latitude.toFloat()
                longitude = latlng.longitude.toFloat()
                Text(
                    text = "Lat: $latitude, \nLng: $longitude"
                )
            }

            ReminderListView(
                modifier = Modifier.fillMaxSize(),
                navController,
                latitude,
                longitude
            )
        }
    }
}

@Composable
fun TopBar(navController: NavController) {
    TopAppBar {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
        Text(text = "All reminders")
    }
}
