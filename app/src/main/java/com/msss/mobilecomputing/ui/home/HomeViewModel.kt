package com.msss.mobilecomputing.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.msss.mobilecomputing.Graph
import com.msss.mobilecomputing.R
import com.msss.mobilecomputing.data.entity.Reminder
import com.msss.mobilecomputing.data.repository.ReminderRepository
import com.msss.mobilecomputing.util.NotificationWorker
import com.msss.mobilecomputing.util.dateToTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state

    init {
        createNotificationChannel(Graph.appContext)
        viewModelScope.launch {
            reminderRepository.allReminders().collect { list ->
                _state.value = HomeViewState(
                    reminders = list
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(context: Context) {
    val name = "NotificationChannelName"
    val descriptionText = "NotificationChannelDescriptionText"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
        description = descriptionText
    }
    val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleReminderNotification(reminder: Reminder) {
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder().build()
    val coroutineScope = rememberCoroutineScope()
    val timeRemaining: Duration = Duration.ofMillis(
        dateToTimestamp(reminder.reminder_time)?.minus(
            dateToTimestamp(LocalDateTime.now())!!)!!)

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(timeRemaining)
        .build()

    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                coroutineScope.launch {
                    Graph.reminderRepository.editReminder(
                        Reminder(
                            reminderId = reminder.reminderId,
                            message = reminder.message,
                            location_x = reminder.location_x,
                            location_y = reminder.location_y,
                            reminder_time = reminder.reminder_time,
                            creation_time = reminder.creation_time,
                            creator_id = reminder.creator_id,
                            reminder_seen = true
                        )
                    )
                }
                createReminderNotification(reminder)
            }
        }
}

private fun createReminderNotification(reminder: Reminder) {
    val notificationId = 1
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Reminder")
        .setContentText("${reminder.message}")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }
}

data class HomeViewState(
    val reminders: List<Reminder> = emptyList()
)
