package com.example.habitstracker.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import java.time.LocalDate

fun List<LocalDate>.chunked(size: Int): List<List<LocalDate>> {
    return this.withIndex().groupBy { it.index / size }.values.map { it.map { it.value } }
}


@RequiresApi(Build.VERSION_CODES.O)
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
