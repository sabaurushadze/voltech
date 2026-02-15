package com.tbc.core_ui.components.item

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import com.tbc.core_ui.components.checkbox.VoltechCheckBoxDefaults
import com.tbc.core_ui.components.image.BaseAsyncImage
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechFixedColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R

@Composable
fun FeedItemCard(
    imageUrl: String?,
    title: String,
    price: String,
    checked: Boolean = false,
    editModeOn: Boolean = false,
    condition: String? = null,
    location: String? = null,
    onRootClick: () -> Unit = {},
) {

    val checkboxWidth by animateDpAsState(
        targetValue = if (editModeOn) Dimen.size48 else Dimen.size0,
        animationSpec = spring(
            stiffness = Spring.StiffnessMediumLow,
            dampingRatio = Spring.DampingRatioNoBouncy
        ),
        label = ""
    )

    val checkboxAlpha by animateFloatAsState(
        targetValue = if (editModeOn) 1f else 0f,
        animationSpec = tween(200),
        label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRootClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(checkboxWidth)
                .alpha(checkboxAlpha),
            contentAlignment = Alignment.Center
        ) {
            Checkbox(
                modifier = Modifier.padding(horizontal = Dimen.size16),
                checked = checked,
                onCheckedChange = null,
                colors = VoltechCheckBoxDefaults.primaryColors
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.size8)
        ) {
            Box(
                modifier = Modifier
                    .height(Dimen.size132)
                    .width(Dimen.size132)
                    .clip(VoltechRadius.radius16)
                    .background(VoltechFixedColor.lightGray)
            ) {
                imageUrl?.let {
                    BaseAsyncImage(
                        url = imageUrl,
                        modifier = Modifier
                            .matchParentSize()
                            .clip(VoltechRadius.radius16),
                    )
                }
            }

            Spacer(modifier = Modifier.width(Dimen.size16))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(Dimen.size4))

                FeedItemContent(
                    title = title,
                    price = price,
                    condition = condition,
                    location = location
                )
            }
        }
    }

}

@Composable
private fun FeedItemContent(
    title: String,
    price: String,
    condition: String?,
    location: String?,
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        color = VoltechColor.foregroundPrimary,
        style = VoltechTextStyle.body,
        maxLines = 3
    )

    Spacer(modifier = Modifier.height(Dimen.size4))

    condition?.let {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = condition,
            color = VoltechColor.foregroundSecondary,
            style = VoltechTextStyle.body,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(Dimen.size8))
    }


    Text(
        modifier = Modifier.fillMaxWidth(),
        text = price,
        color = VoltechColor.foregroundPrimary,
        style = VoltechTextStyle.title2,
        maxLines = 1
    )

    Spacer(modifier = Modifier.height(Dimen.size4))

    location?.let {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = location,
            color = VoltechColor.foregroundSecondary,
            style = VoltechTextStyle.body,
            maxLines = 1
        )
    }
}

@Composable
fun FeedItemPlaceholderCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.size8)
        ) {
            Box(
                modifier = Modifier
                    .height(Dimen.size132)
                    .width(Dimen.size132)
                    .clip(VoltechRadius.radius16)
            ) {
                BaseAsyncImage(
                    errorRes = R.drawable.placeholder,
                    placeholderRes = R.drawable.placeholder,
                    url = "",
                    modifier = Modifier
                        .matchParentSize()
                        .clip(VoltechRadius.radius16),
                )
            }

            Spacer(modifier = Modifier.width(Dimen.size16))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(Dimen.size4))

                FeedItemPlaceholderContent()
            }
        }


    }
}


@Composable
private fun FeedItemPlaceholderContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(Dimen.size16)
            .background(VoltechColor.backgroundTertiary)
    )

    Spacer(modifier = Modifier.height(Dimen.size4))

    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(Dimen.size16)
            .background(VoltechColor.backgroundTertiary)
    )
}