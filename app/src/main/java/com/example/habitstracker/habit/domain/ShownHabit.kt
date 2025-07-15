package com.example.habitstracker.habit.domain

import androidx.room.PrimaryKey
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.core.presentation.utils.toHex

data class ShownHabit (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val iconName: String = "",
    //val isCompleted: Boolean = false,
    val colorHex: String = blueColor.toHex(),
    val days: String = "", // Which days of the week to show a habit (for ex. Mon, wed, Fri,...)
    val executionTime: String = "", // There are 4 variants: Doesn`t matter, Morning, Day, Evening
    val reminder: Boolean = false, // Advanced setting for habit
    val isSelected: Boolean = false
)

fun mapToShownHabits(
    habits: List<HabitEntity>,
    dateHabits: List<DateHabitEntity>,
    selectedDate: String
): List<ShownHabit> {
    return habits
        .filter { habit ->
            dateHabits.any {
                it.habitId == habit.id && it.currentDate == selectedDate
            }
        }
        .map { habit ->
            val isCompleted = dateHabits.any {
                it.habitId == habit.id && it.currentDate == selectedDate && it.isCompleted
            }
            ShownHabit(
                id = habit.id,
                name = habit.name,
                iconName = habit.iconName,
                colorHex = habit.colorHex,
                days = habit.days,
                executionTime = habit.executionTime,
                reminder = habit.reminder,
                isSelected = isCompleted
            )
        }
}