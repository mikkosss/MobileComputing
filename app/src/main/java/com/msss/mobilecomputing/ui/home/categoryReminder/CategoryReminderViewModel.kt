package com.msss.mobilecomputing.ui.home.categoryReminder

import androidx.lifecycle.ViewModel
import com.msss.mobilecomputing.data.entity.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class CategoryReminderViewModel : ViewModel() {
    private val _state = MutableStateFlow(CategoryReminderViewState())

    val state: StateFlow<CategoryReminderViewState>
        get() = _state

    init {
        val list = mutableListOf<Reminder>()
        for (x in 1..20) {
            list.add(
                Reminder(
                    message = "Reminder $x",
                    location_x = 0,
                    location_y = 0,
                    reminder_time = Date(),
                    creation_time = Date(),
                    creator_id = 1,
                    reminder_seen = false
                )
            )
        }
    }
}

data class CategoryReminderViewState(
    val reminders: List<Reminder> = emptyList()
)