package com.tbc.search.presentation.mapper.feed

import com.tbc.search.domain.model.feed.Category
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.Location
import com.tbc.resource.R
import com.tbc.search.presentation.model.feed.UiFeedItem
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun FeedItem.toPresentation(): UiFeedItem {
    return UiFeedItem(
        id = id,
        title = title,
        categoryRes = category.toStringRes(),
        conditionRes = condition.toStringRes(),
        price = price.toPriceUsStyle(),
        images = images,
        quantity = quantity.toString(),
        locationRes = location.toStringRes(),
        userDescription = userDescription,
        sellerAvatar = sellerAvatar,
        sellerUserName = sellerUserName,
    )
}

fun Location.toStringRes(): Int {
    return when (this) {
        Location.DIDI_DIGHOMI -> R.string.didi_digohmi
        Location.GLDANI -> R.string.gldani
    }
}

fun Condition.toStringRes(): Int {
    return when (this) {
        Condition.NEW -> R.string.condition_new
        Condition.USED -> R.string.condition_used
        Condition.PARTS -> R.string.condition_parts
    }
}

fun Category.toStringRes(): Int {
    return when (this) {
        Category.GPU -> R.string.category_gpu
        Category.CPU -> R.string.category_cpu
        Category.MOTHERBOARD -> R.string.category_motherboard
        Category.RAM -> R.string.category_ram
        Category.SSD -> R.string.category_ssd
        Category.HDD -> R.string.category_hdd
        Category.CPU_COOLER -> R.string.category_cpu_cooler
        Category.PSU -> R.string.category_psu
        Category.CASE_COOLER -> R.string.category_case_cooler
        Category.CASE -> R.string.category_case
        Category.MONITOR -> R.string.category_monitor
        Category.CABEL -> R.string.category_cabel
    }
}

fun Double.toPriceUsStyle(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    formatter.currency = Currency.getInstance("USD")
    return formatter.format(this)
}