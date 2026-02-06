package com.tbc.profile.presentation.mapper.edit_profile

import com.tbc.core.domain.model.user.User
import com.tbc.profile.presentation.model.edit_profile.UiUser

fun User.toPresentation() = UiUser(
    uid = uid,
    name = name,
    photoUrl = photoUrl
)