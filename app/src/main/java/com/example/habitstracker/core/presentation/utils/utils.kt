package com.example.habitstracker.core.presentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.presentation.edit_habit.components.SelectedDay
import com.example.habitstracker.core.presentation.theme.blueColor
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

fun getIconName(icon: ImageVector): String {
    return icon.name.split(".")[1]
}

fun iconByName(name: String): ImageVector {
    val cl = Class.forName("androidx.compose.material.icons.filled.${name}Kt")
    val method = cl.declaredMethods.first()
    return method.invoke(null, Icons.Filled) as ImageVector
}

val habitEntityExample = HabitEntity(
0, "habit example", "SentimentVerySatisfied", false,
blueColor.toHex(), "Everyday", "Anytime", false,
)


// Using in Edit/RepeatPicker
fun textState(dayStates: SnapshotStateList<SelectedDay>): Pair<String, String> {
    return when (dayStates.count { it.isSelect == true }) {

        1 -> {
            val oneSelectedDay = dayStates.first { dayItem ->
                dayItem.isSelect == true
            }.day

            val stringForDb = getStringForDbInsert(dayStates)
            Pair(oneSelectedDay, stringForDb)
        }

        2 -> {
            val twoSelectedDays = dayStates.filter { dayItem ->
                dayItem.isSelect
            }.joinToString(", ") { it.day }

            val stringForDb = getStringForDbInsert(dayStates)
            Pair(twoSelectedDays, stringForDb)
        }

        3 -> {
            val threeSelectedDays = dayStates.filter { dayItem ->
                dayItem.isSelect
            }.joinToString(", ") { it.day }

            val stringForDb = getStringForDbInsert(dayStates)
            Pair(threeSelectedDays, stringForDb)
        }

        4 -> {
            val fourSelectedDays = dayStates
                .filter { dayItem -> dayItem.isSelect }
                .take(3)
                .joinToString { it.day } + "..."

            val stringForDb = getStringForDbInsert(dayStates)
            Pair(fourSelectedDays, stringForDb)
        }

        5 -> {
            val twoUnselectedDays = dayStates.filter { dayItem ->
                dayItem.isSelect == false
            }.joinToString(" and ") { it.day }

            val stringForDb = getStringForDbInsert(dayStates)
            Pair("Everyday except $twoUnselectedDays", stringForDb)
        }

        6 -> {
            val oneUnselectedDay = dayStates.filter { dayItem ->
                !dayItem.isSelect
            }.joinToString { it.day }

            val stringForDb = getStringForDbInsert(dayStates)
            Pair("Everyday exept ${oneUnselectedDay}", stringForDb)
        }

        7 -> {
            val stringForDb = getStringForDbInsert(dayStates)
            Pair("Everyday", stringForDb)
        }

        else -> throw IllegalArgumentException("Custom error in RepeatPicker.kt (textState function)")
    }
}

@Composable
fun getCorrectSelectedDaysList(daysFromDb: String): MutableList<SelectedDay> {
    val dayStatesList = mutableListOf(
        SelectedDay(false, "Mon"),
        SelectedDay(false, "Tue"),
        SelectedDay(false, "Wed"),
        SelectedDay(false, "Thu"),
        SelectedDay(false, "Fri"),
        SelectedDay(false, "San"),
        SelectedDay(false, "Sut")
    )

// daysListParam contains list of days, which should be update
    val selectedDaysFromDB = if(daysFromDb != "Everyday") daysFromDb.split(", ")
    else listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sun", "Sut")


// Update state of each day in dayStates
    dayStatesList.forEachIndexed { index, currentDay ->
        dayStatesList[index] = currentDay.copy(
            isSelect = selectedDaysFromDB.contains(currentDay.day)
        )
    }
    return dayStatesList
}

// Return the string that will be push to database as a selectedDays field
fun getStringForDbInsert(dayStates: SnapshotStateList<SelectedDay>): String {
    return dayStates.filter { dayItem ->
        dayItem.isSelect
    }.joinToString(", ") { it.day }
}