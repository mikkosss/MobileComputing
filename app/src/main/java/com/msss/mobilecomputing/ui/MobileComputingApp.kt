package com.msss.mobilecomputing.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.msss.mobilecomputing.MobileComputingAppState
import com.msss.mobilecomputing.rememberMobileComputingAppState
import com.msss.mobilecomputing.ui.home.Home
import com.msss.mobilecomputing.ui.login.Login
import com.msss.mobilecomputing.ui.login.LoginManager
import com.msss.mobilecomputing.ui.profile.Profile
import com.msss.mobilecomputing.ui.reminder.Reminder

@Composable
fun MobileComputingApp(
    context: Context,
    login: LoginManager,
    appState: MobileComputingAppState = rememberMobileComputingAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            Login(navController = appState.navController, context, login)
        }
        composable(route = "home") {
            Home(navController = appState.navController, context, login)
        }
        composable(route = "profile") {
            Profile(navController = appState.navController, context, login)
        }
        composable(route = "reminder") {
            Reminder(navController = appState.navController, context, login)
        }
    }
}