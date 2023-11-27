package com.example.travelapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.travelapp.data.dao.DAOLayer

@Database(entities =  [LoginInfo::class, TravelInfo::class], version = 14, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun daoLayer(): DAOLayer
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Travel_App_Database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}