package com.juhyeon.book.shared.ui.system.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import com.juhyeon.book.shared.ui.extension.textDp
import com.juhyeon.book.shared.ui.system.R

val font = FontFamily(
    Font(R.font.pretendard_regular, weight = FontWeight.Normal),
    Font(R.font.pretendard_bold, weight = FontWeight.Bold),
    Font(R.font.pretendard_light, weight = FontWeight.Light),
    Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
    Font(R.font.pretendard_medium, weight = FontWeight.Medium)
)

@Composable
fun Typography.normal(fontSize: Int, lineHeight: Int): TextStyle =
    TextStyle(
        fontSize = fontSize.textDp,
        fontWeight = FontWeight.Normal,
        fontFamily = font,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeight = lineHeight.textDp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )

@Composable
fun Typography.medium(fontSize: Int, lineHeight: Int): TextStyle =
    TextStyle(
        fontSize = fontSize.textDp,
        fontWeight = FontWeight.Medium,
        fontFamily = font,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeight = lineHeight.textDp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )

@Composable
fun Typography.semiBold(fontSize: Int, lineHeight: Int): TextStyle =
    TextStyle(
        fontSize = fontSize.textDp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = font,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeight = lineHeight.textDp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )

@Composable
fun Typography.bold(fontSize: Int, lineHeight: Int): TextStyle =
    TextStyle(
        fontSize = fontSize.textDp,
        fontWeight = FontWeight.Bold,
        fontFamily = font,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeight = lineHeight.textDp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )

@Composable
fun Typography.normal(fontSize: Int): TextStyle =
    TextStyle(
        fontSize = fontSize.textDp,
        fontWeight = FontWeight.Normal,
        fontFamily = font,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

@Composable
fun Typography.medium(fontSize: Int): TextStyle =
    TextStyle(
        fontSize = fontSize.textDp,
        fontWeight = FontWeight.Medium,
        fontFamily = font,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

@Composable
fun Typography.semiBold(fontSize: Int): TextStyle =
    TextStyle(
        fontSize = fontSize.textDp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = font,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

@Composable
fun Typography.bold(fontSize: Int): TextStyle =
    TextStyle(
        fontSize = fontSize.textDp,
        fontWeight = FontWeight.Bold,
        fontFamily = font,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )