package com.example.travelapp.graph

import android.content.Context
import com.example.travelapp.data.AppDatabase
import com.example.travelapp.repository.OfflineRepository

object Graph {
    lateinit var database:AppDatabase
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