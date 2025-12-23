package com.example.habitstracker.core.presentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.graphics.toColorInt
import com.example.habitstracker.core.presentation.theme.HabitColor
import com.example.habitstracker.habit.domain.ShownHabit
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun List<LocalDate>.chunked(size: Int): List<List<LocalDate>> {
    return this.withIndex().groupBy { it.index / size }.values.map { it.map { it.value } }
}

fun generateDateSequence(startDate: LocalDate, daysCount: Int): List<LocalDate> {
    return List(daysCount) { startDate.plusDays(it.toLong()) }
}

fun Modifier.clickWithRipple(
    color: Color = Color.White,
    onClick: () -> Unit,
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }

    this
        .clickable(
            interactionSource = interactionSource,
            indication = ripple()
        ) {
            onClick()
        }
}

fun Color.toHex(): String {
    val argb = this.toArgb()
    return String.format("#%08X", argb)
}

fun String.getColorFromHex(): Color =
    Color(this.toColorInt())

fun getIconName(icon: ImageVector): String {
    return icon.name.split(".")[1]
}

fun iconByName(name: String): ImageVector {
    val cl = Class.forName("androidx.compose.material.icons.filled.${name}Kt")
    val method = cl.declaredMethods.first()
    return method.invoke(null, Icons.Filled) as ImageVector
}

val shownHabitExample1 = ShownHabit(0, 0, "habit example 1", "SentimentVerySatisfied",
    HabitColor.SkyBlue.light.toHex(), "Everyday", "Anytime", false, false)

val shownHabitExample2 = ShownHabit(1, 0,"habit example 2", "SentimentVerySatisfied",
    HabitColor.Orange.light.toHex(), "Everyday", "Anytime", false, false)

val shownHabitExample3 = ShownHabit(2, 0,"habit example 3", "SentimentVerySatisfied",
    HabitColor.LeafGreen.light.toHex(), "Everyday", "Evening", false, false)

fun getGradientByLightColor(lightColor: Color) = when (lightColor) {
    HabitColor.SkyBlue.light -> HabitColor.SkyBlue
    HabitColor.LeafGreen.light -> HabitColor.LeafGreen
    HabitColor.Amber.light -> HabitColor.Amber
    HabitColor.DeepBlue.light -> HabitColor.DeepBlue
    HabitColor.BrickRed.light -> HabitColor.BrickRed
    HabitColor.Cyan.light -> HabitColor.Cyan
    HabitColor.Orange.light -> HabitColor.Orange
    HabitColor.Teal.light -> HabitColor.Teal
    HabitColor.Golden.light -> HabitColor.Golden
    HabitColor.Lime.light -> HabitColor.Lime
    HabitColor.Aqua.light -> HabitColor.Aqua
    HabitColor.Purple.light -> HabitColor.Purple
    HabitColor.Terracotta.light -> HabitColor.Terracotta
    HabitColor.Rose.light -> HabitColor.Rose
    HabitColor.DarkGreen.light -> HabitColor.DarkGreen
    HabitColor.Sand.light -> HabitColor.Sand

    else -> HabitColor.DefaultColor // fallback
}

fun gradientColor(lightColor: Color, darkColor: Color, radius: Float = 600f): Brush = Brush.radialGradient(
    listOf(lightColor, darkColor),
    center = Offset(50f, 20f),
    radius = radius // Distribution radius
)

fun Long.toFormattedDate(): String {
    val instant = Instant.ofEpochMilli(this)

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())

    return instant.atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}