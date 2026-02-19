package com.tbc.search.domain.usecase.item_details

import javax.inject.Inject

class CalculateTotalFeedbackReceivedUseCase @Inject constructor() {
    operator fun invoke(
        positive: Int,
        neutral: Int,
        negative: Int,
    ): Int {
        return positive + neutral + negative
    }
}
