package com.tbc.core.presentation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toIsoFormat(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return sdf.format(this)
}