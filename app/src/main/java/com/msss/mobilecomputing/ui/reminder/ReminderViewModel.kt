package com.msss.mobilecomputing.ui.reminder

import androidx.lifecycle.ViewModel
import com.msss.mobilecomputing.Graph
import com.msss.mobilecomputing.data.entity.Reminder
import com.msss.mobilecomputing.data.repository.ReminderRepository

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
): ViewModel() {
    suspend fun saveReminder(reminder: Reminder): Long {
        return reminderRepository.addReminder(reminder)
    }

    suspend fun editReminder(reminder: Reminder) {
        return reminderRepository.editReminder(reminder)
    }

    fun getReminder(reminderId: Long): Reminder? {
        return reminderRepository.getReminderWithId(reminderId)
    }

    suspend fun deleteReminder(reminderId: Long): Unit? {
        return reminderRepository.deleteReminder(reminderId)
    }
}