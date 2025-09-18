package com.example.habitstracker.history.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statistic_table")
data class StatisticEntity (
    @PrimaryKey val id: Int = 0,
    val completedHabits: Int,
    val bestStreak: Int,
    val perfectDays: Int
)