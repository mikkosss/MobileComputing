package com.msss.mobilecomputing.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.msss.mobilecomputing.MobileComputingAppState
import com.msss.mobilecomputing.data.entity.Reminder
import com.msss.mobilecomputing.rememberMobileComputingAppState
import com.msss.mobilecomputing.ui.home.Home
import com.msss.mobilecomputing.ui.login.Login
import com.msss.mobilecomputing.ui.login.LoginManager
import com.msss.mobilecomputing.ui.profile.Profile
import com.msss.mobilecomputing.ui.reminder.Reminder

@RequiresApi(Build.VERSION_CODES.O)
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
        composable(route = "reminder/{edit}/{reminderId}",
            arguments = listOf(
                navArgument("edit") { type = NavType.BoolType; defaultValue = false },
                navArgument("reminderId") { type = NavType.LongType; defaultValue = 0 })
        ) {
            val edit = it.arguments!!.getBoolean("edit")
            val id = it.arguments!!.getLong("reminderId")
            Reminder(navController = appState.navController, context, login, edit, id)
        }
    }
}