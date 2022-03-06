package com.msss.mobilecomputing.ui.reminder

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.model.LatLng
import com.msss.mobilecomputing.data.entity.Reminder
import com.msss.mobilecomputing.ui.login.LoginManager
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Reminder(
    navController: NavController,
    context: Context,
    login: LoginManager,
    edit: Boolean,
    id: Long
) {
    navController.clearBackStack("reminder")

    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val viewModel: ReminderViewModel = viewModel()
    val reminder: Reminder

    val reminderId: Long
    val message = rememberSaveable { mutableStateOf("") }
    val locationX = rememberSaveable { mutableStateOf("") }
    val locationY = rememberSaveable { mutableStateOf("") }
    val reminderTime = rememberSaveable { mutableStateOf("") }
    val creationTime = rememberSaveable { mutableStateOf("") }
    val creatorId = rememberSaveable { mutableStateOf("") }
    val reminderSeen: Boolean

    val year = rememberSaveable { mutableStateOf("") }
    val month = rememberSaveable { mutableStateOf("") }
    val day = rememberSaveable { mutableStateOf("") }
    val hour = rememberSaveable { mutableStateOf("") }
    val minute = rememberSaveable { mutableStateOf("") }

    if (edit) {
        reminder = viewModel.getReminder(id)!!

        reminderId = reminder.reminderId
        message.value = reminder.message
        locationX.value = reminder.location_x.toString()
        locationY.value = reminder.location_y.toString()
        reminderTime.value = reminder.reminder_time.toString()
        creationTime.value = reminder.creation_time.toString()
        creatorId.value = reminder.creator_id.toString()
        year.value = reminder.reminder_time.year.toString()
        month.value = isSingleDigit(reminder.reminder_time.monthValue)
        day.value = isSingleDigit(reminder.reminder_time.dayOfMonth)
        hour.value = isSingleDigit(reminder.reminder_time.hour)
        minute.value = isSingleDigit(reminder.reminder_time.minute)
        reminderSeen = reminder.reminder_seen
    }
    else {
        reminderId = 0
        year.value = LocalDateTime.now().year.toString()
        month.value = LocalDateTime.now().monthValue.toString()
        day.value = LocalDateTime.now().dayOfMonth.toString()
        reminderSeen = false
    }

    val latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value

    Surface(modifier = Modifier.clickable(onClick = { focusManager.clearFocus() }))
    {
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
                Text(text = "Reminder")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = message.value,
                    onValueChange = { message.value = it },
                    label = { Text("Message") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(10.dp))
                /*Text("Location")
                Row {
                    OutlinedTextField(
                        value = locationX.value,
                        onValueChange = { locationX.value = it },
                        label = { Text("X") },
                        modifier = Modifier.fillMaxWidth(0.5f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Right
                            )
                        })
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = locationY.value,
                        onValueChange = { locationY.value = it },
                        label = { Text("Y") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Down
                            )
                        })
                    )
                }*/
                if (latlng == null) {
                    OutlinedButton(
                        onClick = { navController.navigate("map") },
                        modifier = Modifier.height(55.dp)
                    ) {
                        Text(text = "Reminder location")
                    }
                } else {
                    Text(
                        text = "Lat: ${latlng.latitude}, \nLng: ${latlng.longitude}"
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    OutlinedTextField(
                        value = year.value,
                        onValueChange = { year.value = it },
                        label = { Text("yyyy") },
                        modifier = Modifier.requiredWidthIn(min = 50.dp, max = 60.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Right) }
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    OutlinedTextField(
                        value = month.value,
                        onValueChange = { month.value = it },
                        label = { Text("MM") },
                        modifier = Modifier.requiredWidthIn(min = 50.dp, max = 60.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Right) }
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    OutlinedTextField(
                        value = day.value,
                        onValueChange = { day.value = it },
                        label = { Text("dd") },
                        modifier = Modifier.requiredWidthIn(min = 50.dp, max = 60.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Right) }
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    OutlinedTextField(
                        value = hour.value,
                        onValueChange = { hour.value = it },
                        label = { Text("HH") },
                        modifier = Modifier.requiredWidthIn(min = 50.dp, max = 60.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Right) }
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    OutlinedTextField(
                        value = minute.value,
                        onValueChange = { minute.value = it },
                        label = { Text("mm") },
                        modifier = Modifier.requiredWidthIn(min = 50.dp, max = 60.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.clearFocus() }
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    enabled = true,
                    onClick = {
                        if (edit) {
                            if (message.value != "") {
                                coroutineScope.launch {
                                    if (
                                        year.value != "" &&
                                        month.value != "" &&
                                        day.value != "" &&
                                        hour.value != "" &&
                                        minute.value != ""
                                    ) {
                                        reminderTime.value = year.value + "-" +
                                                isSingleDigit(month.value.toInt()) + "-" +
                                                isSingleDigit(day.value.toInt()) + "T" +
                                                isSingleDigit(hour.value.toInt()) + ":" +
                                                isSingleDigit(minute.value.toInt())
                                    } else {
                                        reminderTime.value = ""
                                    }
                                    viewModel.editReminder(
                                        Reminder(
                                            reminderId = reminderId,
                                            message = message.value,
                                            location_x = try {
                                                locationX.value.toFloat()
                                            } catch (e: NumberFormatException) {
                                                0.0f
                                            },
                                            location_y = try {
                                                locationY.value.toFloat()
                                            } catch (e: NumberFormatException) {
                                                0.0f
                                            },
                                            reminder_time = when {
                                                reminderTime.value != "" -> stringToDate(reminderTime.value)
                                                else -> stringToDate(creationTime.value)
                                            },
                                            creation_time = stringToDate(creationTime.value),
                                            creator_id = creatorId.value.toLong(),
                                            reminder_seen = reminderSeen
                                        )
                                    )
                                }
                                navController.popBackStack()
                            } else {
                                val toast = Toast.makeText(
                                    context,
                                    "Enter reminder message",
                                    Toast.LENGTH_SHORT
                                )
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
                            }
                        }
                        else {
                            if (message.value != "") {
                                coroutineScope.launch {
                                    if (
                                        year.value != "" &&
                                        month.value != "" &&
                                        day.value != "" &&
                                        hour.value != "" &&
                                        minute.value != ""
                                    ) {
                                        reminderTime.value = year.value + "-" +
                                                isSingleDigit(month.value.toInt()) + "-" +
                                                isSingleDigit(day.value.toInt()) + "T" +
                                                isSingleDigit(hour.value.toInt()) + ":" +
                                                isSingleDigit(minute.value.toInt())
                                    } else {
                                        reminderTime.value = ""
                                    }
                                    viewModel.saveReminder(
                                        Reminder(
                                            message = message.value,
                                            location_x = if (latlng != null) {
                                                try {
                                                    latlng.longitude.toFloat()
                                                } catch (e: NumberFormatException) {
                                                    0.0f
                                                }
                                            } else 0.0f,
                                            location_y = if(latlng != null) {
                                                try {
                                                    latlng.latitude.toFloat()
                                                } catch (e: NumberFormatException) {
                                                    0.0f
                                                }
                                            } else 0.0f,
                                            reminder_time = when {
                                                reminderTime.value != "" -> stringToDate(reminderTime.value)
                                                else -> LocalDateTime.now()
                                            },
                                            creation_time = LocalDateTime.now(),
                                            creator_id = login.displayUsername().hashCode()
                                                .toLong(),
                                            reminder_seen = reminderSeen
                                        )
                                    )
                                }
                                navController.popBackStack()
                            } else {
                                val toast = Toast.makeText(
                                    context,
                                    "Enter reminder message",
                                    Toast.LENGTH_SHORT
                                )
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun stringToDate(text: String): LocalDateTime {
    return LocalDateTime.parse(text)
}

private fun isSingleDigit(number: Int): String {
    return if (number in 1..9) "0$number"
    else "$number"
}