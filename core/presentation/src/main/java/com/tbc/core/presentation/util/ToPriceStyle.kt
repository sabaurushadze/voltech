package com.tbc.core.presentation.util

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun Double.toPriceUsStyle(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    formatter.currency = Currency.getInstance("USD")
    return formatter.format(this)
}