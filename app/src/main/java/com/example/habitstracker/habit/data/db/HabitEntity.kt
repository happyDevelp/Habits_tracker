package com.example.habitstracker.habit.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.utils.toHex

const val TABLE_NAME = "habit_table"

@Entity(tableName = TABLE_NAME)
data class HabitEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val iconName: String = "",
    val isDone: Boolean = false,
    val colorHex: String = blueColor.toHex(),
    val days: String = "", // Which days of the week to show a habit (for ex. Mon, wed, Fri,...)
    val executionTime: String = "", // There are 4 variants: Doesn`t matter, Morning, Day, Evening
    val reminder: Boolean = false // Advanced setting for habit
)
