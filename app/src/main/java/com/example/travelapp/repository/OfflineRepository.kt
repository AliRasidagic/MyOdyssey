package com.example.travelapp.repository

import com.example.travelapp.data.DAOLayer
import com.example.travelapp.data.LoginInfo
import com.example.travelapp.data.TravelInfo

class OfflineRepository(
    private val daoLayer: DAOLayer
): TravelAppRepository {

    override suspend fun getUserWithEmail(email: String): LoginInfo? = daoLayer.getUserWithEmail(email)

    override suspend fun countCountries(): Int = daoLayer.countCountries()

    override suspend fun countCities(): Int = daoLayer.countCities()

    override suspend fun countContinents(): Int = daoLayer.countContinents()

    override suspend fun countTrips(): Int = daoLayer.countTrips()

    override suspend fun getUsername(): String = daoLayer.getUsername()

    override fun getImage(): String = daoLayer.getImage()

    override suspend fun getTrips(): List<TravelInfo> = daoLayer.getTrips()

    override suspend fun getCountries(): List<String> = daoLayer.getCountries()

    override suspend fun insertImage(image: String) = daoLayer.insertImage(image)

    override suspend fun insertUser(loginInfo: LoginInfo) = daoLayer.insertUser(loginInfo)

    override suspend fun insertTrip(travelInfo: TravelInfo) = daoLayer.insertTrip(travelInfo)
}