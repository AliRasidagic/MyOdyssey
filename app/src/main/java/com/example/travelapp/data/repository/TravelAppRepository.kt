package com.example.travelapp.data.repository

import com.example.travelapp.data.database.LoginInfo
import com.example.travelapp.data.database.TravelInfo

interface TravelAppRepository {

    suspend fun getUserWithEmail(email: String): LoginInfo?

    suspend fun countCountries(): Int

    suspend fun countCities(): Int

    suspend fun countContinents(): Int

    suspend fun countTrips(): Int

    suspend fun getUsername(): String

    suspend fun getPassword(): String

    suspend fun getImage(): String

    suspend fun getTrips(): List<TravelInfo>

    suspend fun getCountries(): List<String>

    suspend fun insertImage(image: String)

    suspend fun updateUsername(username: String)

    suspend fun updatePassword(password: String)


    suspend fun insertUser(loginInfo: LoginInfo)

    suspend fun insertTrip(travelInfo: TravelInfo)
}