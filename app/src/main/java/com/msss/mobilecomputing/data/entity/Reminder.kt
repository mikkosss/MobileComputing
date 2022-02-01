package com.msss.mobilecomputing.data.entity

import java.util.*


data class Reminder(
    var message: String,
    var location_x: Long,
    var location_y: Long,
    var reminder_time: Date?,
    var creation_time: Date?,
    val creator_id: Long,
    var reminder_seen: Boolean,
)
