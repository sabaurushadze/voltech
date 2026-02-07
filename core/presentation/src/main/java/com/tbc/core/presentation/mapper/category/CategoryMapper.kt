package com.tbc.core.presentation.mapper.category

import com.tbc.core.domain.model.category.Category
import com.tbc.resource.R

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
