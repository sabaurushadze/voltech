package com.tbc.selling.domain.usecase.selling.add_item.form

import javax.inject.Inject

class ValidateTitleUseCase @Inject constructor() {
    operator fun invoke(title: String): Boolean {
        return title.length in MIN_TITLE_LENGTH..MAX_TITLE_LENGTH && title.isNotBlank()
    }

    companion object {
        private const val MIN_TITLE_LENGTH = 2
        private const val MAX_TITLE_LENGTH = 80
    }
}
