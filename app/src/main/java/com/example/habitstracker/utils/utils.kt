package com.example.habitstracker.utils


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import java.time.LocalDate

val APP_VERSION = "0.0.0 (alpha)"
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
