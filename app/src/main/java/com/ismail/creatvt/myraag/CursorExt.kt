package com.ismail.creatvt.myraag

import android.database.Cursor

fun Cursor.getStrColumn(columnName: String): String {
    return getString(getColumnIndex(columnName))
}

fun Cursor.getIntColumn(columnName: String): Int {
    return getInt(getColumnIndex(columnName))
}