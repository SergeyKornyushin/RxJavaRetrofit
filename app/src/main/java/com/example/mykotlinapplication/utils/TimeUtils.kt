package com.example.mykotlinapplication.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.*

fun convertTimestampToTime(timeStamp: Int?): String{
    if (timeStamp == null) return ""
    val sdf = SimpleDateFormat("HH:mm:ss", getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(Timestamp(timeStamp.toLong()*1000).time))
}