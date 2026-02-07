package com.tbc.core.presentation.extension

import java.util.Locale

fun String.capitalizeFirst(): String {
    val locale = Locale.getDefault()
    return lowercase(locale)
        .replaceFirstChar { it.titlecase(locale) }
}
