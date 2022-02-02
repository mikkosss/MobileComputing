package com.msss.mobilecomputing.ui.login

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Login(
    navController: NavController,
    context: Context,
    login: LoginManager
) {

    val focusManager = LocalFocusManager.current

    Surface(modifier = Modifier
        .fillMaxSize()
        .clickable(onClick = { focusManager.clearFocus() })
    ) {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = username.value,
                onValueChange = { data -> username.value = data.trim()},
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = { data -> password.value = data.trim()},
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (username.value != "" && password.value != "") {
                        if (login.checkCredentials(username.value, password.value)) {
                            val toast = Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER,0,0)
                            toast.show()
                            navController.navigate("home")
                        }
                        else {
                            val toast = Toast.makeText(context, "Enter valid username and password", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER,0,0)
                            toast.show()
                        }
                    }
                    else {
                        val toast = Toast.makeText(context, "Enter valid username and password", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER,0,0)
                        toast.show()
                    }
                },
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (username.value != "" && password.value != "") {
                        if (login.userExist(username.value)) {
                            val toast = Toast.makeText(context, "Username already in use", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER,0,0)
                            toast.show()
                        }
                        else {
                            login.createUser(username.value, password.value)
                            val toast = Toast.makeText(context, "User created", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            navController.navigate("home")
                        }
                    }
                    else {
                        val toast = Toast.makeText(context, "Enter valid username and password", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER,0,0)
                        toast.show()
                    }
                },
                enabled = true,
                modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Create user")
            }
        }
    }
}

//@Preview
//@Composable
//fun Preview() {
//    Login(navController = NavController())
//}