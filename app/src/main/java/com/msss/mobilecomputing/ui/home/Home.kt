package com.msss.mobilecomputing.ui.home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.msss.mobilecomputing.R
import com.msss.mobilecomputing.ui.home.categoryReminder.CategoryReminder
import com.msss.mobilecomputing.ui.login.LoginManager

@Composable
fun Home(
//    viewModel: HomeViewModel = viewModel(),
    navController: NavController,
    context: Context,
    login: LoginManager
) {

    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "reminder") },
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
                modifier = Modifier.fillMaxSize()
            )
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
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = stringResource(R.string.account))
            }
        }
    )
}
