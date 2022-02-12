package com.msss.mobilecomputing

import android.content.Context
import androidx.room.Room
import com.msss.mobilecomputing.data.repository.ReminderRepository
import com.msss.mobilecomputing.data.room.MobileComputingDatabase

object Graph {
    lateinit var database: MobileComputingDatabase
        private set

    val reminderRepository by lazy {
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, MobileComputingDatabase::class.java, "data.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}