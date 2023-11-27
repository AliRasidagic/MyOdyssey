package com.example.travelapp.data.graph

import android.content.Context
import com.example.travelapp.data.database.AppDatabase
import com.example.travelapp.data.repository.OfflineRepository

object Graph {
    lateinit var database: AppDatabase
        private set

    val repository by lazy {
        OfflineRepository(
            daoLayer = database.daoLayer()
        )
    }

    fun provide(context: Context){
        database = AppDatabase.getDatabase(context)
    }
}