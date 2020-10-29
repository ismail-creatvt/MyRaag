package com.ismail.creatvt.myraag

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Duration(val hours: Int, val minutes: Int, val seconds: Int) : Parcelable {
    companion object {

        val ZERO_SECONDS = ofSeconds(0)

        fun ofSeconds(seconds: Int): Duration {
            var totalSeconds = seconds
            //calculate minutes
            var totalMinutes = totalSeconds / 60
            //get remaining seconds
            totalSeconds = totalSeconds % 60
            //Calculate hours
            val totalHours = totalMinutes / 60
            //get remaining minutes
            totalMinutes = totalMinutes % 60

            return Duration(totalHours, totalMinutes, totalSeconds)
        }
    }

    val totalSeconds: Int
        get() = (hours * 60 * 60) + (minutes * 60) + seconds

    override fun toString(): String {
        if (hours != 0) {
            return String.format("%02d:%02d:%02d",hours, minutes, seconds)
        }
        return String.format("%02d:%02d", minutes, seconds)
    }
}