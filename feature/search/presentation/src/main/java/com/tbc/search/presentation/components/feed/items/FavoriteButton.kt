package com.tbc.search.presentation.components.feed.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.resource.R

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onFavoriteIconClick: () -> Unit,
    iconSize: Dp,
    iconContainerSize: Dp,
) {
    val heartIcon = if (isSelected) R.drawable.ic_filled_heart else R.drawable.ic_outlined_heart

    Box(
        modifier = modifier
            .clip(VoltechRadius.radius64)
            .clickable { onFavoriteIconClick() }
            .size(Dimen.size48),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .shadow(
                    elevation = Dimen.size2,
                    shape = VoltechRadius.radius64,
                    clip = false
                )
                .clip(VoltechRadius.radius64)
                .size(iconContainerSize)
                .background(VoltechColor.backgroundPrimary.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = heartIcon),
                contentDescription = "",
                tint = VoltechColor.foregroundPrimary,
                modifier = Modifier
                    .size(iconSize)
            )
        }
    }

}