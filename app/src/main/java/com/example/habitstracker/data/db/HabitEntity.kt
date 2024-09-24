package com.example.habitstracker.data.db

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
data class HabitEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val icon: ImageVector,
    val isDone: Boolean,
    val color: Color,
    val days: String, // Which days of the week to show a habit (for ex. Mon, wed, Fri,...)
    val executionTime: String, // There are 4 variants: Doesn`t matter, Morning, Day, Evening
    val reminder: Boolean = false // Advanced setting for habit
)