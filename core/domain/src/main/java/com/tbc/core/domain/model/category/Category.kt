package com.tbc.core.domain.model.category

enum class Category {
    GPU, CPU, MOTHERBOARD, RAM, SSD, HDD, CPU_COOLER, PSU, CASE_COOLER, CASE, MONITOR, CABEL;

    companion object {
        fun fromString(value: String): Category {
            return when (value) {
                "GPU" -> GPU
                "CPU" -> CPU
                "MOTHERBOARD" -> MOTHERBOARD
                "RAM" -> RAM
                "SSD" -> SSD
                "HDD" -> HDD
                "CPU_COOLER" -> CPU_COOLER
                "PSU" -> PSU
                "CASE_COOLER" -> CASE_COOLER
                "CASE" -> CASE
                "MONITOR" -> MONITOR
                "CABEL" -> CABEL
                else -> GPU
            }
        }

        fun toServerString(category: Category): String {
            return when (category) {
                GPU -> "GPU"
                CPU -> "CPU"
                MOTHERBOARD -> "MOTHERBOARD"
                RAM -> "RAM"
                SSD -> "SSD"
                HDD -> "HDD"
                CPU_COOLER -> "CPU_COOLER"
                PSU -> "PSU"
                CASE_COOLER -> "CASE_COOLER"
                CASE -> "CASE"
                MONITOR -> "MONITOR"
                CABEL -> "CABEL"
            }
        }
    }
}