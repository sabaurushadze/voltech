package com.tbc.profile.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tbc.core_ui.components.button.SecondaryButton
import com.tbc.core_ui.theme.Dimen
import com.tbc.resource.R

@Composable
fun ProfilePictureSheet(
    url: String? = null,
    onChooseExistingPhotoClick: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onRemovePhotoClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.size16)
    ) {
        url?.let {
            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.remove_photo),
                onClick = { onRemovePhotoClick() }
            )
        }

        Spacer(modifier = Modifier.height(Dimen.size16))

        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.choose_existing_photo),
            onClick = { onChooseExistingPhotoClick() }
        )

        Spacer(modifier = Modifier.height(Dimen.size16))

        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.take_photo),
            onClick = { onTakePhotoClick() }
        )
    }
}