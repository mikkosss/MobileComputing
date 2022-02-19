package com.msss.mobilecomputing.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.msss.mobilecomputing.ui.login.LoginManager
import com.msss.mobilecomputing.ui.theme.MobileComputingTheme

class MainActivity : ComponentActivity() {
    lateinit var login: LoginManager
    lateinit var context: Context

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = applicationContext
        login = LoginManager(context)

        setContent {
            MobileComputingTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MobileComputingApp(context, login)
                }
            }
        }
    }
}