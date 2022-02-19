package com.msss.mobilecomputing.data.repository

import com.msss.mobilecomputing.data.entity.Reminder
import com.msss.mobilecomputing.data.room.ReminderDao
import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDao
) {
    fun allReminders(): Flow<List<Reminder>> = reminderDao.allReminders()
    fun reminders(): Flow<List<Reminder>> = reminderDao.reminders()
    fun getReminderWithId(reminderId: Long) : Reminder? = reminderDao.getReminderWithId(reminderId)

    suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)
    suspend fun editReminder(reminder: Reminder) = reminderDao.update(reminder)
    suspend fun deleteReminder(reminderId: Long): Unit? {
        val reminder = getReminderWithId(reminderId)
        return reminder?.let { reminderDao.delete(it) }
    }
}