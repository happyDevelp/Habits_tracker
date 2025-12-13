package com.example.habitstracker.habit.domain

import com.example.habitstracker.core.presentation.theme.MyPalette.blueColor
import com.example.habitstracker.core.presentation.utils.toHex
import java.time.LocalDate

data class ShownHabit (
    val habitId: Int = 0,
    val dateHabitId: Int = 0,
    val name: String = "",
    val iconName: String = "",
    //val isCompleted: Boolean = false,
    val colorHex: String = blueColor.toHex(),
    val days: String = "", // Which days of the week to show a habit (for ex. Mon, wed, Fri,...)
    val executionTime: String = "", // There are 4 variants: Doesn`t matter, Morning, Day, Evening
    val reminder: Boolean = false, // Advanced setting for habit
    val isSelected: Boolean = false
)

fun ShownHabit.toDateHabitEntity(date: String) = DateHabitEntity(
    id = dateHabitId,
    habitId = habitId,
    currentDate = date,
    completed = isSelected
)

fun mapToShownHabits(
    habits: List<HabitEntity>,
    dateHabits: List<DateHabitEntity>,
    selectedDate: String
): List<ShownHabit> {
    return habits
        // we leave only those habits for which there is an appointment for this date
        .filter { habit ->
            dateHabits.any {
                it.habitId == habit.id && it.currentDate == selectedDate
            }
        }
        // building a ShownHabit already knowing a specific DateHabit
        .mapNotNull { habit ->
            val dateHabit = dateHabits.firstOrNull {
                it.habitId == habit.id && it.currentDate == selectedDate
            }

            if (dateHabit == null) {
                null // should theoretically not happen, because we have already filtered above
            } else {
                ShownHabit(
                    habitId = habit.id,
                    dateHabitId = dateHabit.id,
                    name = habit.name,
                    iconName = habit.iconName,
                    colorHex = habit.colorHex,
                    days = habit.days,
                    executionTime = habit.executionTime,
                    reminder = habit.reminder,
                    isSelected = dateHabit.completed
                )
            }
        }
}