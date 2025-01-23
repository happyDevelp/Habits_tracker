package com.example.habitstracker.habit.domain

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// The Domain layer is independent of data sources.

const val HABIT_STATUS_TABLE_NAME = "habit_status_table"

@Entity(tableName = HABIT_STATUS_TABLE_NAME)
data class HabitStatusEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val habitId: Int,
    val date: String, // YYYY-MM-DD
    val isCompleted: Boolean
)