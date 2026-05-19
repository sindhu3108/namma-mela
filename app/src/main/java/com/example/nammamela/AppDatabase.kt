package com.example.nammamela

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Seat::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun seatDao(): SeatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "seat_database"
                )
                .allowMainThreadQueries() // Using for simplicity as per user request
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
