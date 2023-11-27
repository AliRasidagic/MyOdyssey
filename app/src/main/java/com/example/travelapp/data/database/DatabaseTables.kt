package com.example.travelapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LoginInfo")
data class LoginInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "Username")
    val username: String,

    @ColumnInfo(name = "E-mail")
    val email: String,

    @ColumnInfo(name = "Password")
    val password: String,

    @ColumnInfo(name = "Profile Picture")
    val profilePicture: String
)

@Entity(tableName = "TravelInfo")
data class TravelInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "Title")
    val title: String,

    @ColumnInfo(name = "Continent")
    val continent: String,

    @ColumnInfo(name = "Country")
    val country: String,

    @ColumnInfo(name = "City")
    val city: String,

    @ColumnInfo(name = "Start Date")
    val startDate: String,

    @ColumnInfo(name = "End Date")
    val endDate: String,

    @ColumnInfo(name = "Information")
    val info: String,

    @ColumnInfo(name = "Image")
    val image: String
)