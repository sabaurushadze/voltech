package com.tbc.core_ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Text
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
    text: String,
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
    text: String,
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
    onClick: () -> Unit
) {
    BaseOutlinedButton(
        modifier = modifier,
        colors = VoltechButtonDefaults.tertiaryColors,
        enabled = enabled,
        shape = VoltechRadius.radius24,
        onClick = onClick,
        border = BorderStroke(VoltechBorder.medium, VoltechColor.foregroundPrimary)
    ) {
        Text(
            text = text,
            color = VoltechColor.foregroundPrimary,
            style = VoltechTextStyle.bodyBold
        )
    }
}

@Composable
fun BorderlessButton(
    modifier: Modifier = Modifier,
    text: String,
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
        Text(
            text = text,
            color = VoltechColor.foregroundPrimary,
            style = VoltechTextStyle.bodyBold
        )
    }
}

@Composable
fun CircleIconButton(
    modifier: Modifier,
    onClick : () -> Unit,
    size: Dp,
    iconColor: Color,
    backgroundColor: Color,
){
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
            painter = painterResource(R.drawable.ic_shopping_cart),
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
            SecondaryButton (
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
            PrimaryIconButton (
                text = "Buy it now",
                enabled = true,
                icon = R.drawable.ic_outlined_heart,
                onClick = {}
            )
            SecondaryIconButton(
                text = "Buy it now",
                enabled = true,
                icon = R.drawable.ic_outlined_heart,
                onClick = {}
            )
            BorderlessIconButton(
                text = "Buy it now",
                enabled = true,
                icon = R.drawable.ic_outlined_heart,
                onClick = {}
            )
        }
    }
}
