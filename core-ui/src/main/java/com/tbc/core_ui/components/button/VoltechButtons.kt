package com.tbc.core_ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechBorder
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechFixedColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.core_ui.theme.VoltechTheme
import com.tbc.resource.R

@Composable
fun PrimaryIconButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    BaseFilledButton(
        modifier = modifier,
        colors = VoltechButtonDefaults.primaryColors,
        enabled = enabled,
        shape = VoltechRadius.radius24,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(Dimen.size16),
                imageVector = ImageVector.vectorResource(icon),
                tint = VoltechColor.foregroundOnAccent,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(Dimen.size4))

            Text(
                text = text,
                color = VoltechColor.foregroundOnAccent,
                style = VoltechTextStyle.bodyBold
            )
        }

    }
}

@Composable
fun PrimaryDoubleIconButton(
    modifier: Modifier = Modifier,
    leftText: String,
    rightText: String,
    @DrawableRes leftIcon: Int,
    @DrawableRes rightIcon: Int,
    enabled: Boolean = true,
    leftOnClick: () -> Unit,
    rightOnClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = VoltechRadius.radius24,
        color = VoltechColor.foregroundAccent,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // LEFT SIDE
            Row(modifier = Modifier
                .clickable(enabled = enabled) { leftOnClick() }
                .padding(
                    horizontal = Dimen.size16,
                    vertical = Dimen.size12
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Icon(
                    modifier = Modifier.size(Dimen.size16),
                    imageVector = ImageVector.vectorResource(leftIcon),
                    contentDescription = null,
                    tint = VoltechFixedColor.white
                )

                Spacer(Modifier.width(Dimen.size6))

                Text(
                    text = leftText,
                    style = VoltechTextStyle.bodyBold,
                    color = VoltechFixedColor.white
                )
            }


            VerticalDivider(
                modifier = Modifier.height(Dimen.size24),
                color = VoltechFixedColor.white,
                thickness = Dimen.size2
            )

            // RIGHT SIDE
            Row(modifier = Modifier
                .clickable(enabled = enabled) { rightOnClick() }
                .padding(
                    horizontal = Dimen.size16,
                    vertical = Dimen.size12
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Icon(
                    modifier = Modifier.size(Dimen.size16),
                    imageVector = ImageVector.vectorResource(rightIcon),
                    contentDescription = null,
                    tint = VoltechFixedColor.white
                )

                Spacer(Modifier.width(Dimen.size6))

                Text(
                    text = rightText,
                    style = VoltechTextStyle.bodyBold,
                    color = VoltechFixedColor.white
                )
            }
            }
        }
}


@Composable
fun SecondaryIconButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    BaseOutlinedButton(
        modifier = modifier,
        colors = VoltechButtonDefaults.secondaryColors,
        enabled = enabled,
        shape = VoltechRadius.radius24,
        onClick = onClick,
        border = BorderStroke(VoltechBorder.medium, VoltechColor.foregroundAccent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(Dimen.size16),
                imageVector = ImageVector.vectorResource(icon),
                tint = VoltechColor.foregroundAccent,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(Dimen.size4))

            Text(
                text = text,
                color = VoltechColor.foregroundAccent,
                style = VoltechTextStyle.bodyBold
            )
        }

    }
}

@Composable
fun TertiaryIconButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int,
    enabled: Boolean = true,
    iconSize: Dp = Dimen.size16,
    borderThickness: Dp = VoltechBorder.medium,
    borderColor: Color = VoltechColor.foregroundPrimary,
    errorText: String = "",
    showText: Boolean = true,
    onClick: () -> Unit
) {
    Column {
        BaseOutlinedButton(
            modifier = modifier,
            colors = VoltechButtonDefaults.secondaryColors,
            enabled = enabled,
            shape = VoltechRadius.radius24,
            onClick = onClick,
            border = BorderStroke(borderThickness, borderColor)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = ImageVector.vectorResource(icon),
                    tint = VoltechColor.foregroundPrimary,
                    contentDescription = null,
                )

                if (showText) {
                    Spacer(modifier = Modifier.width(Dimen.size4))

                    Text(
                        text = text,
                        color = VoltechColor.foregroundPrimary,
                        style = VoltechTextStyle.bodyBold
                    )
                }
            }
        }
        if (errorText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Dimen.size4))
            Text(
                text = errorText,
                color = VoltechColor.foregroundAttention,
                style = VoltechTextStyle.body
            )
        }
    }


}


@Composable
fun BorderlessIconButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    BaseFilledButton(
        modifier = modifier,
        colors = VoltechButtonDefaults.borderlessColors,
        enabled = enabled,
        shape = VoltechRadius.radius24,
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = VoltechColor.foregroundAccent,
                style = VoltechTextStyle.bodyBold
            )

            Spacer(modifier = Modifier.width(Dimen.size4))

            Icon(
                modifier = Modifier.size(Dimen.size16),
                imageVector = ImageVector.vectorResource(icon),
                tint = VoltechColor.foregroundAccent,
                contentDescription = null,
            )
        }

    }
}

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String, enabled:
    Boolean = true,
    onClick: () -> Unit
) {
    BaseFilledButton(
        modifier = modifier,
        colors = VoltechButtonDefaults.primaryColors,
        enabled = enabled,
        shape = VoltechRadius.radius24,
        onClick = onClick
    ) {
        Text(
            text = text,
            color = VoltechColor.foregroundOnAccent,
            style = VoltechTextStyle.bodyBold
        )
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String, enabled:
    Boolean = true,
    onClick: () -> Unit
) {
    BaseOutlinedButton(
        modifier = modifier,
        colors = VoltechButtonDefaults.secondaryColors,
        enabled = enabled,
        shape = VoltechRadius.radius24,
        onClick = onClick,
        border = BorderStroke(VoltechBorder.medium, VoltechColor.foregroundAccent)
    ) {
        Text(
            text = text,
            color = VoltechColor.foregroundAccent,
            style = VoltechTextStyle.bodyBold
        )
    }
}

@Composable
fun TertiaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    border: BorderStroke = BorderStroke(
        VoltechBorder.medium,
        VoltechColor.foregroundPrimary
    ),
    onClick: () -> Unit
) {
    BaseOutlinedButton(
        modifier = modifier,
        colors = VoltechButtonDefaults.tertiaryColors,
        enabled = enabled,
        shape = VoltechRadius.radius24,
        onClick = onClick,
        border = border
    ) {
        Text(
            text = text,
            color = if (enabled) VoltechColor.foregroundPrimary else VoltechColor.foregroundDisabled,
            style = VoltechTextStyle.bodyBold
        )
    }
}

@Composable
fun BorderlessButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    textColor: Color = VoltechColor.foregroundPrimary,
    onClick: () -> Unit
) {
    BaseFilledButton(
        modifier = modifier,
        colors = VoltechButtonDefaults.borderlessColors,
        enabled = enabled,
        shape = VoltechRadius.radius24,
        onClick = onClick,
    ) {
        Text(
            text = text,
            color = textColor,
            style = VoltechTextStyle.bodyBold
        )
    }
}

@Composable
fun CircleIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes iconRes: Int,
    size: Dp,
    iconColor: Color,
    backgroundColor: Color,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(Dimen.size6)
            .clip(CircleShape),
        colors = iconButtonColors(
            containerColor = backgroundColor
        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(size),
            tint = iconColor,
        )
    }
}

@Composable
fun TertiaryCircleIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    enabled: Boolean = true,
    size: Dp,
    iconColor: Color,
    backgroundColor: Color,
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .padding(Dimen.size6)
            .clip(CircleShape),
        colors = iconButtonColors(
            containerColor = backgroundColor
        )
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(size),
            tint = iconColor,
        )
    }
}

@Composable
private fun BaseFilledButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors,
    enabled: Boolean = true,
    shape: RoundedCornerShape,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Button(
        modifier = modifier.height(Dimen.size48),
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        shape = shape,
    ) {
        content()
    }
}

@Composable
private fun BaseOutlinedButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors,
    enabled: Boolean = true,
    border: BorderStroke,
    shape: RoundedCornerShape,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    OutlinedButton(
        modifier = modifier.height(Dimen.size48),
        onClick = onClick,
        border = border,
        enabled = enabled,
        colors = colors,
        shape = shape,
    ) {
        content()
    }
}


@Composable
@Preview(showSystemUi = true)
fun PrimaryButtonPreview() {
    VoltechTheme(darkTheme = false) {
        Column() {
            PrimaryButton(
                text = "Buy it now",
                enabled = true,
                onClick = {}
            )
            SecondaryButton(
                text = "Buy it now",
                enabled = true,
                onClick = {}
            )
            TertiaryButton(
                text = "Buy it now",
                enabled = true,
                onClick = {}
            )
            BorderlessButton(
                text = "Buy it now",
                enabled = true,
                onClick = {}
            )
            PrimaryIconButton(
                text = "Buy it now",
                enabled = true,
                icon = R.drawable.ic_outlined_heart,
                onClick = {})
            PrimaryDoubleIconButton(
                leftText = "Sort",
                rightText = "Filter",
                enabled = true,
                leftIcon = R.drawable.ic_sort,
                rightIcon = R.drawable.ic_filter,
                leftOnClick = {},
                rightOnClick = {},
            )
            SecondaryIconButton(
                text = "Buy it now",
                enabled = true,
                icon = R.drawable.ic_outlined_heart,
                onClick = {})
            BorderlessIconButton(
                text = "Buy it now",
                enabled = true,
                icon = R.drawable.ic_outlined_heart,
                onClick = {})
            TertiaryIconButton(
                text = "Buy it now",
                enabled = true,
                icon = R.drawable.ic_outlined_heart,
                errorText = "Please select at least one image",
                onClick = {})
        }
    }
}
