package com.example.habitstracker.history.domain

import com.example.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getAllAchievements(): Flow<List<AchievementEntity>>

    fun getAllDatesForStreak(): Flow<List<DateHabitEntity>>
}