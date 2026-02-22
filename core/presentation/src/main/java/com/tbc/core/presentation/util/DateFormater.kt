package com.tbc.core.presentation.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun String.toFormattedDate(): String {
    val instant = Instant.parse(this)
    val date = instant
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    return "%02d/%02d/%04d".format(
        date.dayOfMonth,
        date.monthNumber,
        date.year
    )
}