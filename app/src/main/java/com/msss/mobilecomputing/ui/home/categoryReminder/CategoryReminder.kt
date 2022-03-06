package com.msss.mobilecomputing.ui.home.categoryReminder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.msss.mobilecomputing.data.entity.Reminder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CategoryReminder(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: CategoryReminderViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        ReminderList(
            list = viewState.reminders,
            navController,
            viewModel,
            coroutineScope
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ReminderList(
    list: List<Reminder>,
    navController: NavController,
    viewModel: CategoryReminderViewModel,
    coroutineScope: CoroutineScope
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(list) { item ->
            ReminderListItem(
                reminder = item,
                navController,
                viewModel,
                coroutineScope,
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ReminderListItem(
    reminder: Reminder,
    navController: NavController,
    viewModel: CategoryReminderViewModel,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier.clickable {
        val reminderId = reminder.reminderId
        navController.navigate(route = "reminder/true/$reminderId")
    }) {
        val (divider, message, locationX, locationY, reminder_time, delete) = createRefs()
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // message
        Text(
            text = reminder.message,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(message) {
                linkTo(
                    start = parent.start,
                    end = delete.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // location latitude
        Text(
            text = "Latitude: " + reminder.location_y.toString(),
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(locationY) {
                linkTo(
                    start = parent.start,
                    end = delete.start,
                    startMargin = 24.dp,
                    endMargin = 8.dp,
                    bias = 0f
                )
                top.linkTo(message.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // location longitude
        Text(
            text = "Longitude: " + reminder.location_x.toString(),
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(locationX) {
                linkTo(
                    start = parent.start,
                    end = delete.start,
                    startMargin = 24.dp,
                    endMargin = 8.dp,
                    bias = 0f
                )
                top.linkTo(locationY.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // reminder time
        Text(
            text = dateFormatter(reminder.reminder_time),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(reminder_time) {
                linkTo(
                    start = parent.start,
                    end = delete.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(locationX.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // icon
        IconButton(
            onClick = {
                val reminderId = reminder.reminderId
                coroutineScope.launch { viewModel.deleteReminder(reminderId) }
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(delete) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "delete"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun dateFormatter(
    date: LocalDateTime
): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy", Locale.getDefault())
    return date.format(formatter)
}