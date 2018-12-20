package com.didik.footballmatchschedule.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {

    private fun setDate(date: String, format: String, isDate: Boolean): String {
        var result = ""
        val before = SimpleDateFormat(if (isDate) "yyyy-MM-dd" else "HH:mm:ss", Locale.getDefault())
        before.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val beforeDate = before.parse(date)
            val newDate = SimpleDateFormat(format)

            result = newDate.format(beforeDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }

    fun dateFormat(date: String?): String {
        return setDate(date.toString(), "EEE, dd MMMM yyyy", true)
    }

    fun timeFormat(time: String?): String {
        return setDate(time.toString(), "HH:mm", false)
    }

    @SuppressLint("SimpleDateFormat")
    fun toGMTFormat(date: String, time: String): Date? {
        val formatter = SimpleDateFormat("dd/MM/yy HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$date $time"
        return formatter.parse(dateTime)
    }
}