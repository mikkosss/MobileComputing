package com.msss.mobilecomputing.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
fun fromTimestamp(value: Long?): LocalDateTime? {
    return value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC) }
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateToTimestamp(date: LocalDateTime?): Long? {
    return date?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
}