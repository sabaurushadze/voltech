package com.tbc.core.domain.util

inline fun <reified T : Enum<T>> enumValueOfOrNull(
    value: String
): T? {
    return enumValues<T>()
        .firstOrNull { it.name.equals(value, ignoreCase = true) }
}

inline fun <reified T : Enum<T>> T.toServerString(): String {
    return this.name
}