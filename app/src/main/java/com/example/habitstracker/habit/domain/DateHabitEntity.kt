package com.example.habitstracker.habit.domain

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// The Domain layer is independent of data sources.

const val DATE_TABLE_NAME = "date_table"

@Entity(
    tableName = DATE_TABLE_NAME,
    foreignKeys = [
        ForeignKey(entity = HabitEntity::class, parentColumns = ["id"], childColumns = ["habitId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class DateHabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val habitId: Int,
    val date: String, // YYYY-MM-DD
    val isCompleted: Boolean
)