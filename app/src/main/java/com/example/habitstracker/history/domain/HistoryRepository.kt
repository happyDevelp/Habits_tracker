package com.example.habitstracker.history.domain

import com.example.habitstracker.habit.domain.DateHabitEntity

interface HistoryRepository {
    suspend fun getAllDatesForStreak(): List<DateHabitEntity>
}