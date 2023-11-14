package com.example.travelapp.converter

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun dateFormatter(date: LocalDate): String {
    return SimpleDateFormat("dd-mm-yyyy").format(date)
}

@SuppressLint("SimpleDateFormat")
fun DateParser(dateString: String): Date? {
    return SimpleDateFormat("dd-MM-yyyy").parse(dateString)
}