package com.tbc.search.domain.usecase.item_details

import java.util.Locale
import javax.inject.Inject

class CalculateSellerPositiveFeedbackUseCase @Inject constructor() {
    operator fun invoke(
        positive: Int,
        negative: Int,
    ): Double {
        val total = positive + negative

        if (total == 0) return 100.0

        return ((positive).toDouble() / total * 100).let {
            String.format(Locale.US, "%.1f", it).toDouble()
        }
    }
}
