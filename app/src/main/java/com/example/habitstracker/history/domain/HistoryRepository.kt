package com.example.habitstracker.history.domain

import com.example.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun getAllDatesForStreak(): Flow<List<DateHabitEntity>>
}