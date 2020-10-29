package com.ismail.creatvt.myraag

import java.util.*

class DateUtils {

    companion object{
        fun fromSeconds(seconds:Int): Date {
            val date = Date()
            date.time = seconds * 1000L
            return date
        }
    }
}