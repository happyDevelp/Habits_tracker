package com.example.habitstracker.history.domain

import com.example.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun updateUnlockedDate(unlockedAt: String, isNotified: Boolean, id: Int)

    fun getAllAchievements(): Flow<List<AchievementEntity>>

    fun getAllDatesForStreak(): Flow<List<DateHabitEntity>>
}