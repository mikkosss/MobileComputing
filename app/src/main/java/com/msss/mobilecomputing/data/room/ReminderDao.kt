package com.msss.mobilecomputing.data.room

import androidx.room.*
import com.msss.mobilecomputing.data.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ReminderDao {

    @Query(value = "SELECT * FROM reminders WHERE id = :reminderId")
    abstract fun getReminderWithId(reminderId: Long): Reminder?

    @Query("SELECT * FROM reminders")
    abstract fun reminders(): Flow<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Reminder)

    @Delete
    abstract suspend fun delete(entity: Reminder)
}