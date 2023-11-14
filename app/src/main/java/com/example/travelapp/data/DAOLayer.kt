package com.example.travelapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAOLayer {

    @Query("SELECT * FROM LoginInfo WHERE `E-mail` = :email")
    suspend fun getUserWithEmail(email: String): LoginInfo?

    @Query("SELECT COUNT(DISTINCT `Country`) FROM TravelInfo")
    suspend fun countCountries(): Int

    @Query("SELECT COUNT(DISTINCT `City`) FROM TravelInfo")
    suspend fun countCities(): Int

    @Query("SELECT COUNT(DISTINCT `Continent`) FROM TravelInfo")
    suspend fun countContinents(): Int

    @Query("SELECT COUNT(DISTINCT `Id`) FROM TravelInfo")
    suspend fun countTrips(): Int

    @Query("SELECT `Username` FROM LoginInfo")
    suspend fun getUsername(): String

    @Query("SELECT `Profile Picture` FROM LoginInfo")
    fun getImage(): String

    @Query("SELECT * FROM TravelInfo")
    suspend fun getTrips(): List<TravelInfo>

    @Query("SELECT DISTINCT`Country` FROM TravelInfo")
    suspend fun getCountries(): List<String>

    @Query("UPDATE LoginInfo SET `Profile Picture` = :image WHERE id = 1")
    suspend fun insertImage(image: String)

    @Insert
    suspend fun insertUser(loginInfo: LoginInfo)

    @Insert
    suspend fun insertTrip(travelInfo: TravelInfo)
}