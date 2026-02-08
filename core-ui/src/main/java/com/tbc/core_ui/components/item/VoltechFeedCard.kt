package com.tbc.core_ui.components.item

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.tbc.core_ui.components.checkbox.VoltechCheckBoxDefaults
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.core_ui.components.image.BaseAsyncImage

@Composable
fun FeedItemCard(
    imageUrl: String,
    title: String,
    price: String,
    checked: Boolean = false,
    editModeOn: Boolean = false,
    condition: String? = null,
    location: String? = null,
    onRootClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRootClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (editModeOn) {
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
            ) {
                BaseAsyncImage(
                    url = imageUrl,
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