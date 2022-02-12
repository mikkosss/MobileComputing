package com.msss.mobilecomputing.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msss.mobilecomputing.data.entity.Reminder

@Database(
    entities = [Reminder::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class MobileComputingDatabase : RoomDatabase(){
    abstract fun reminderDao(): ReminderDao
}