package com.chiper.kz.theme.glass

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object GlassGradients {
    val MainBackground = listOf(
        Color(0xFF0D1117),
        Color(0xFF161B22),
        Color(0xFF0D1117)
    )

    val AuroraBackground = listOf(
        Color(0xFF1A0D2E),
        Color(0xFF0D1A2E),
        Color(0xFF0D2E1A),
        Color(0xFF2E1A0D)
    )

    val SunsetBackground = listOf(
        Color(0xFF2E0D1A),
        Color(0xFF1A2E0D),
        Color(0xFF0D1A2E),
        Color(0xFF1A0D2E)
    )

    val OceanBackground = listOf(
        Color(0xFF0D1A2E),
        Color(0xFF0D2E2E),
        Color(0xFF0D2E1A),
        Color(0xFF1A0D2E)
    )

    val GlassPrimary = listOf(
        Color(0xFF2AABEE).copy(alpha = 0.2f),
        Color(0xFF229ED9).copy(alpha = 0.1f),
        Color(0xFF1A73E8).copy(alpha = 0.05f)
    )

    val GlassSuccess = listOf(
        Color(0xFF4DCD5E).copy(alpha = 0.2f),
        Color(0xFF3CC44D).copy(alpha = 0.1f),
        Color(0xFF2EB83E).copy(alpha = 0.05f)
    )

    val GlassError = listOf(
        Color(0xFFE53935).copy(alpha = 0.2f),
        Color(0xFFD32F2F).copy(alpha = 0.1f),
        Color(0xFFC62828).copy(alpha = 0.05f)
    )

    val GlassWarm = listOf(
        Color(0xFFFBBC05).copy(alpha = 0.2f),
        Color(0xFFF9A825).copy(alpha = 0.1f),
        Color(0xFFF57F17).copy(alpha = 0.05f)
    )

    val IslandBackground = listOf(
        Color(0xFF0A0E17),
        Color(0xFF0F1520),
        Color(0xFF141D2E),
        Color(0xFF0D1A2E)
    )
}

object GlassTypography {
    val DisplayLarge = TextStyle(
        fontSize = 56.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 64.sp,
        letterSpacing = (-0.5f).sp
    )

    val DisplayMedium = TextStyle(
        fontSize = 44.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    )

    val DisplaySmall = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    )

    val HeadlineLarge = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    )

    val HeadlineMedium = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    )

    val HeadlineSmall = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    )

    val TitleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )

    val TitleMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    )

    val TitleSmall = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )

    val BodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    val BodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    )

    val BodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    )

    val LabelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )

    val LabelMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

    val LabelSmall = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
}