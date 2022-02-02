package com.msss.mobilecomputing.data.entity

import java.util.*

data class Reminder(
    val message: String,
    val locationName: String,
    val location_x: Long,
    val location_y: Long,
    val reminder_time: Date?,
    val creation_time: Date?,
    val creator_id: Long,
    var reminder_seen: Boolean,
)
