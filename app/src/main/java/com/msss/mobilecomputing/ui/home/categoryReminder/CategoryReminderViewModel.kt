package com.msss.mobilecomputing.ui.home.categoryReminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msss.mobilecomputing.Graph
import com.msss.mobilecomputing.data.entity.Reminder
import com.msss.mobilecomputing.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class CategoryReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryReminderViewState())

    val state: StateFlow<CategoryReminderViewState>
        get() = _state

    init {
        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = CategoryReminderViewState(
                    reminders = list
                )
            }
        }
    }

    suspend fun deleteReminder(reminderId: Long): Unit? {
        return reminderRepository.deleteReminder(reminderId)
    }
}

data class CategoryReminderViewState(
    val reminders: List<Reminder> = emptyList()
)