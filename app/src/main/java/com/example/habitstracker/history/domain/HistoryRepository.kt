package com.example.habitstracker.history.domain

import com.example.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun updateUnlockedDate(unlockedAt: String, isNotified: Boolean, id: Int)

    fun getAllAchievements(): Flow<List<AchievementEntity>>

    fun getAllDatesForStreak(): Flow<List<DateHabitEntity>>

    suspend fun insertStatistic(statisticEntity: StatisticEntity)

    suspend fun updateStatistic(completedHabits: Int, bestStreak: Int, perfectDays: Int)
    fun getStatistic(): Flow<StatisticEntity>

    suspend fun getStatisticOnce(): StatisticEntity?
}