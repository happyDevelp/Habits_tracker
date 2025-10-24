package com.example.habitstracker.core.presentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.habitstracker.core.presentation.theme.HabitColor
import com.example.habitstracker.habit.domain.ShownHabit
import java.time.LocalDate

val APP_VERSION = "0.0.6 (alpha)"
fun List<LocalDate>.chunked(size: Int): List<List<LocalDate>> {
    return this.withIndex().groupBy { it.index / size }.values.map { it.map { it.value } }
}

fun generateDateSequence(startDate: LocalDate, daysCount: Int): List<LocalDate> {
    return List(daysCount) { startDate.plusDays(it.toLong()) }
}

@Composable
fun Modifier.clickWithRipple(
    color: Color = Color.White,
    onClick: () -> Unit,
): Modifier {
    return composed {
        this.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(color = color),
            onClick = onClick
        )
    }
}

fun Color.toHex(): String {
    val argb = this.toArgb()
    return String.format("#%08X", argb)
}

fun String.getColorFromHex(): Color =
    Color(android.graphics.Color.parseColor(this))

fun getIconName(icon: ImageVector): String {
    return icon.name.split(".")[1]
}

fun iconByName(name: String): ImageVector {
    val cl = Class.forName("androidx.compose.material.icons.filled.${name}Kt")
    val method = cl.declaredMethods.first()
    return method.invoke(null, Icons.Filled) as ImageVector
}

val shownHabitExample1 = ShownHabit(0, "habit example 1", "SentimentVerySatisfied",
    HabitColor.SkyBlue.light.toHex(), "Everyday", "Anytime", false, false)

val shownHabitExample2 = ShownHabit(1, "habit example 2", "SentimentVerySatisfied",
    HabitColor.Orange.light.toHex(), "Everyday", "Anytime", false, false)

val shownHabitExample3 = ShownHabit(2, "habit example 3", "SentimentVerySatisfied",
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