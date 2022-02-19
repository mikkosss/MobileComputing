package com.msss.mobilecomputing

import android.content.Context
import androidx.room.Room
import com.msss.mobilecomputing.data.repository.ReminderRepository
import com.msss.mobilecomputing.data.room.MobileComputingDatabase

object Graph {
    lateinit var database: MobileComputingDatabase
        private set

    lateinit var appContext: Context

    val reminderRepository by lazy {
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    fun provide(context: Context) {
        appContext = context
        database = Room.databaseBuilder(context, MobileComputingDatabase::class.java, "data.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}