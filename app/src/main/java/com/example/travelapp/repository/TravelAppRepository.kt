package com.example.travelapp.repository

import androidx.room.Query
import com.example.travelapp.data.LoginInfo
import com.example.travelapp.data.TravelInfo

interface TravelAppRepository {

    suspend fun getUserWithEmail(email: String): LoginInfo?

    suspend fun countCountries(): Int

    suspend fun countCities(): Int

    suspend fun countContinents(): Int

    suspend fun countTrips(): Int

    suspend fun getUsername(): String

    fun getImage(): String

    suspend fun getTrips(): List<TravelInfo>

    suspend fun getCountries(): List<String>

    suspend fun insertImage(image: String)

    suspend fun insertUser(loginInfo: LoginInfo)

    suspend fun insertTrip(travelInfo: TravelInfo)
}