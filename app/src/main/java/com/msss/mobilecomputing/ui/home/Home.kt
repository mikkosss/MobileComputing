package com.msss.mobilecomputing.ui.home

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.msss.mobilecomputing.R
import com.msss.mobilecomputing.ui.home.categoryReminder.CategoryReminder
import com.msss.mobilecomputing.ui.login.LoginManager
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
//    viewModel: HomeViewModel = viewModel(),
    navController: NavController,
    context: Context,
    login: LoginManager
) {
    navController.clearBackStack("login")

    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "reminder/false/0") },
                modifier = Modifier.padding(all = 20.dp)
            ) {
               Icon(
                  imageVector = Icons.Default.Add,
                  contentDescription = null
               )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            //val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f)

            HomeAppBar(
                //backgroundColor = appBarColor,
                navController
            )

            CategoryReminder(
                modifier = Modifier.fillMaxSize(),
                navController
            )

            HomeView()
        }
    }
}

@Composable
private fun HomeAppBar(
    //backgroundColor: Color,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                //color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        //backgroundColor = backgroundColor,
        actions = {
            IconButton(onClick = {
                navController.navigate(route = "reminderList")
            }) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null
                )
            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = stringResource(R.string.account))
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeView() {
    val viewModel: HomeViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()

    val list = viewState.reminders

    list.forEach {
        if (it.reminder_time.toLocalDate() == LocalDate.now()) {
            if (it.reminder_time.toLocalTime().isAfter(LocalTime.now())) {
                ScheduleReminderNotification(it)
            }
        }
    }
}