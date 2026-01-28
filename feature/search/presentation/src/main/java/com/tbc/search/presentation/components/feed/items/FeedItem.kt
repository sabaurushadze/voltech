package com.tbc.search.presentation.components.feed.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechRadius
import com.tbc.core.designsystem.theme.VoltechTextStyle
import com.tbc.core.designsystem.theme.VoltechTheme
import com.tbc.core.presentation.base.BaseAsyncImage
import com.tbc.search.presentation.R

@Composable
fun FeedItemCard(
    imageUrl: String,
    title: String,
    condition: String,
    price: String,
    location: String,
    isFavoriteIconSelected: Boolean,
    onFavoriteIconClick: () -> Unit,
    onRootClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRootClick() }
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
                    url = imageUrl,
                    modifier = Modifier
                        .matchParentSize(),
                )


                FavoriteButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    isSelected = isFavoriteIconSelected,
                    onFavoriteIconClick = { onFavoriteIconClick() }
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
    condition: String,
    location: String,
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        color = VoltechColor.onBackground,
        style = VoltechTextStyle.body18Normal,
        maxLines = 3
    )

    Spacer(modifier = Modifier.height(Dimen.size4))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = condition,
        color = VoltechColor.neutralText1,
        style = VoltechTextStyle.body16Normal,
        maxLines = 1
    )

    Spacer(modifier = Modifier.height(Dimen.size8))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = price,
        color = VoltechColor.onBackground,
        style = VoltechTextStyle.body22Bold,
        maxLines = 1
    )

    Spacer(modifier = Modifier.height(Dimen.size4))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = location,
        color = VoltechColor.neutralText1,
        style = VoltechTextStyle.body14Normal,
        maxLines = 1
    )
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
                        .matchParentSize(),
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
            .background(VoltechColor.neutral1)
    )

    Spacer(modifier = Modifier.height(Dimen.size4))

    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(Dimen.size16)
            .background(VoltechColor.neutral1)
    )
}

@Composable
private fun FavoriteButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onFavoriteIconClick: () -> Unit,
) {
    val heartIcon = if (isSelected) R.drawable.ic_filled_heart else R.drawable.ic_outlined_heart

    Box(
        modifier = modifier
            .size(Dimen.size50)
            .clickable { onFavoriteIconClick() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.size32)
                .shadow(
                    elevation = Dimen.size2,
                    shape = VoltechRadius.radius64,
                    clip = false
                )
                .clip(VoltechRadius.radius64)
                .background(VoltechColor.background.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = heartIcon),
                contentDescription = "",
                tint = VoltechColor.onBackground,
                modifier = Modifier.size(Dimen.size18)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FeedItemWrapperPreview() {
    VoltechTheme() {
        FeedItemCard(
            isFavoriteIconSelected = true,
            onFavoriteIconClick = { },

            onRootClick = {},
            imageUrl = "",
            title = "RTX 4060 super duper magari umagresi video barati",
            condition = "New",
            price = "$1,780.00",
            location = "Located in Didi Dighomi"
        )
    }
}