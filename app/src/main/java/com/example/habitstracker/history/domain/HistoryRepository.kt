package com.example.habitstracker.history.domain

import com.example.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun insertAchievements(achievementEntityList: List<AchievementEntity>)

    suspend fun getAchievementOnce(): AchievementEntity?

    fun getAllAchievements(): Flow<List<AchievementEntity>>

    suspend fun updateUnlockedDate(unlockedAt: String, isNotified: Boolean, id: Int)

    fun getAllDatesForStreak(): Flow<List<DateHabitEntity>>
}