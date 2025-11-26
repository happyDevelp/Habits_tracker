package com.example.habitstracker.habit.data.db

import com.example.habitstracker.habit.domain.ShownHabit

data class HabitWithDateDb(
    val habitId: Int,
    val dateHabitId: Int,
    val name: String,
    val iconName: String,
    val colorHex: String,
    val days: String,
    val executionTime: String,
    val reminder: String,
    val isSelected: Boolean,
)


fun HabitWithDateDb.toShownHabit() = ShownHabit(
    habitId = habitId,
    dateHabitId = dateHabitId,
    name = name,
    iconName = iconName,
    colorHex = colorHex,
    days = days,
    executionTime = executionTime,
    isSelected = isSelected
)