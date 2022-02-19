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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderList(
    navController: NavController
) {
    Scaffold {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            TopBar(navController)

            ReminderListView(
                modifier = Modifier.fillMaxSize(),
                navController
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
