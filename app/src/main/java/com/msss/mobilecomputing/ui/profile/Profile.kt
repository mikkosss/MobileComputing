package com.msss.mobilecomputing.ui.profile

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
import androidx.compose.material.icons.filled.ArrowBack
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
import com.msss.mobilecomputing.ui.login.LoginManager

@Composable
fun Profile(
    navController: NavController,
    context: Context,
    login: LoginManager
) {

    val focusManager = LocalFocusManager.current

    Surface(modifier = Modifier.clickable(onClick = { focusManager.clearFocus() })
    ) {
        val currentPassword = rememberSaveable { mutableStateOf("") }
        val newPassword = rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )

                }
                Text(text = "Profile")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = login.displayUsername().toString(),
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .heightIn(max = 24.dp)
                )
                Spacer(modifier = Modifier.height(9.dp))
                OutlinedTextField(
                    value = currentPassword.value,
                    onValueChange = { data -> currentPassword.value = data.trim() },
                    label = { Text("Current password") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(9.dp))
                OutlinedTextField(
                    value = newPassword.value,
                    onValueChange = { data -> newPassword.value = data.trim() },
                    label = { Text("New password") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(9.dp))
                Button(
                    onClick = {
                        if (currentPassword.value != "" && newPassword.value != "") {
                            if (login.changePass(currentPassword.value, newPassword.value)) {
                                val toast =
                                    Toast.makeText(context, "Password changed", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
                                navController.navigate("home")
                            } else {
                                val toast = Toast.makeText(
                                    context,
                                    "Current password wrong",
                                    Toast.LENGTH_SHORT
                                )
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
                            }
                        }
                        else {
                            val toast = Toast.makeText(
                                context,
                                "Input empty",
                                Toast.LENGTH_SHORT
                            )
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    },
                    enabled = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = "Change password")
                }
                Spacer(modifier = Modifier.height(9.dp))
                Button(
                    onClick = {
                        val toast = Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        navController.navigate("login")
                    },
                    enabled = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = "Logout")
                }
            }
        }
    }
}