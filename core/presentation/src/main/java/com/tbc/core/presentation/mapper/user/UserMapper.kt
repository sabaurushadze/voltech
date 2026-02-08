package com.tbc.core.presentation.mapper.user

import com.tbc.core.domain.model.user.User
import com.tbc.core.presentation.model.UiUser

fun User.toPresentation() = UiUser(
    uid = uid,
    name = name,
    photoUrl = photoUrl
)