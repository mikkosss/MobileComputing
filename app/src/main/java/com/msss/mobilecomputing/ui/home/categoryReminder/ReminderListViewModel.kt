package com.msss.mobilecomputing.ui.home.categoryReminder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msss.mobilecomputing.Graph
import com.msss.mobilecomputing.data.entity.Reminder
import com.msss.mobilecomputing.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ReminderListViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ReminderListViewState())

    val state: StateFlow<ReminderListViewState>
        get() = _state

    init {
        viewModelScope.launch {
            reminderRepository.allReminders().collect { list ->
                _state.value = ReminderListViewState(
                    reminders = list
                )
            }
        }
    }

    suspend fun deleteReminder(reminderId: Long): Unit? {
        return reminderRepository.deleteReminder(reminderId)
    }
}

data class ReminderListViewState(
    val reminders: List<Reminder> = emptyList()
)