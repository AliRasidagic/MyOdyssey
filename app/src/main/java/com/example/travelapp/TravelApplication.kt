package com.example.travelapp

import android.app.Application
import com.example.travelapp.graph.Graph

class TravelApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}