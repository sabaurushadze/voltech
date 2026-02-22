package com.tbc.core_ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.tbc.core_ui.R

val LocalTypography = compositionLocalOf { VoltechTypography() }

val VoltechTextStyle: VoltechTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current

val MarketSans = FontFamily(
    Font(R.font.marketsans_regular, FontWeight.Normal),
    Font(R.font.marketsans_bold, FontWeight.Bold)
)


private object VoltechLetterSpacing {
    val display1 = (-0.92).sp
    val display2 = (-0.72).sp
    val display3 = (-0.6).sp
    val none = 0.sp
    val signal1 = 0.7.sp
    val signal2 = 0.5.sp
}

private object VoltechLineHeight {
    val lineHeight150 = 12.sp
    val lineHeight200 = 16.sp
    val lineHeight250 = 20.sp
    val lineHeight300 = 24.sp
    val lineHeight350 = 28.sp
    val lineHeight400 = 32.sp
    val lineHeight500 = 40.sp
    val lineHeight575 = 46.sp
    val lineHeight600 = 56.sp
}

private object VoltechFontSize {
    val body = 14.sp
    val giant1 = 30.sp
    val giant2 = 36.sp
    val giant3 = 46.sp
    val large1 = 20.sp
    val large2 = 24.sp
    val medium = 16.sp
    val small = 12.sp
    val smallest = 10.sp
}

private object VoltechFontWeight {
    val W400 = FontWeight.Normal
    val W600 = FontWeight.Bold
}

@Immutable
data class VoltechTypography(
    val display1: TextStyle = TextStyle(
        fontSize = VoltechFontSize.giant3,
        fontWeight = VoltechFontWeight.W600,
        letterSpacing = VoltechLetterSpacing.display1,
        lineHeight = VoltechLineHeight.lineHeight600,
        fontFamily = MarketSans,
    ),
    val display2: TextStyle = TextStyle(
        fontSize = VoltechFontSize.giant2,
        fontWeight = VoltechFontWeight.W600,
        letterSpacing = VoltechLetterSpacing.display2,
        lineHeight = VoltechLineHeight.lineHeight575,
        fontFamily = MarketSans,
    ),
    val display3: TextStyle = TextStyle(
        fontSize = VoltechFontSize.giant1,
        fontWeight = VoltechFontWeight.W600,
        letterSpacing = VoltechLetterSpacing.display3,
        lineHeight = VoltechLineHeight.lineHeight500,
        fontFamily = MarketSans,
    ),
    val title1: TextStyle = TextStyle(
        fontSize = VoltechFontSize.large2,
        fontWeight = VoltechFontWeight.W600,
        letterSpacing = VoltechLetterSpacing.none,
        lineHeight = VoltechLineHeight.lineHeight400,
        fontFamily = MarketSans,
    ),
    val title2: TextStyle = TextStyle(
        fontSize = VoltechFontSize.large1,
        fontWeight = VoltechFontWeight.W600,
        letterSpacing = VoltechLetterSpacing.none,
        lineHeight = VoltechLineHeight.lineHeight350,
        fontFamily = MarketSans,
    ),
    val title3: TextStyle = TextStyle(
        fontSize = VoltechFontSize.medium,
        fontWeight = VoltechFontWeight.W600,
        letterSpacing = VoltechLetterSpacing.none,
        lineHeight = VoltechLineHeight.lineHeight300,
        fontFamily = MarketSans,
    ),
    val subtitle1: TextStyle = TextStyle(
        fontSize = VoltechFontSize.large1,
        fontWeight = VoltechFontWeight.W400,
        letterSpacing = VoltechLetterSpacing.none,
        lineHeight = VoltechLineHeight.lineHeight350,
        fontFamily = MarketSans,
    ),
    val subtitle2: TextStyle = TextStyle(
        fontSize = VoltechFontSize.medium,
        fontWeight = VoltechFontWeight.W400,
        letterSpacing = VoltechLetterSpacing.none,
        lineHeight = VoltechLineHeight.lineHeight300,
        fontFamily = MarketSans,
    ),
    val body: TextStyle = TextStyle(
        fontSize = VoltechFontSize.body,
        fontWeight = VoltechFontWeight.W400,
        letterSpacing = VoltechLetterSpacing.none,
        lineHeight = VoltechLineHeight.lineHeight250,
        fontFamily = MarketSans,
    ),
    val bodyBold: TextStyle = TextStyle(
        fontSize = VoltechFontSize.body,
        fontWeight = VoltechFontWeight.W600,
        letterSpacing = VoltechLetterSpacing.none,
        lineHeight = VoltechLineHeight.lineHeight250,
        fontFamily = MarketSans,
    ),
    val bodyUnderLine: TextStyle = TextStyle(
        fontSize = VoltechFontSize.body,
        fontWeight = VoltechFontWeight.W400,
        textDecoration = TextDecoration.Underline,
        letterSpacing = VoltechLetterSpacing.none,
        lineHeight = VoltechLineHeight.lineHeight250,
        fontFamily = MarketSans,
    ),
    val caption: TextStyle = TextStyle(
        fontSize = VoltechFontSize.small,
        fontWeight = VoltechFontWeight.W400,
        letterSpacing = VoltechLetterSpacing.none,
        lineHeight = VoltechLineHeight.lineHeight200,
        fontFamily = MarketSans,
    ),
    val captionBold: TextStyle = TextStyle(
        fontSize = VoltechFontSize.small,
        fontWeight = VoltechFontWeight.W600,
        letterSpacing = VoltechLetterSpacing.none,
        lineHeight = VoltechLineHeight.lineHeight200,
        fontFamily = MarketSans,
    ),

    val signal1: TextStyle = TextStyle(
        fontSize = VoltechFontSize.body,
        fontWeight = VoltechFontWeight.W400,
        letterSpacing = VoltechLetterSpacing.signal1,
        lineHeight = VoltechLineHeight.lineHeight250,
        fontFamily = MarketSans,
    ),
    val signal2: TextStyle = TextStyle(
        fontSize = VoltechFontSize.smallest,
        fontWeight = VoltechFontWeight.W600,
        letterSpacing = VoltechLetterSpacing.signal2,
        lineHeight = VoltechLineHeight.lineHeight150,
        fontFamily = MarketSans,
    )
)