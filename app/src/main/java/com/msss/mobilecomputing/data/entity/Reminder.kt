package com.msss.mobilecomputing.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "reminders",
    indices = [
        Index("id", unique = true)
    ]
)
data class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val reminderId: Long = 0,
    @ColumnInfo(name = "reminder_message") val message: String,
    @ColumnInfo(name = "reminder_location_x") val location_x: Float,
    @ColumnInfo(name = "reminder_location_y") val location_y: Float,
    @ColumnInfo(name = "reminder_time") val reminder_time: LocalDateTime,
    @ColumnInfo(name = "reminder_creation_time") val creation_time: LocalDateTime,
    @ColumnInfo(name = "reminder_creator_id") val creator_id: Long,
    @ColumnInfo(name = "reminder_seen") val reminder_seen: Boolean,
)
