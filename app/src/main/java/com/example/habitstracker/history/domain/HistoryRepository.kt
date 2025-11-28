package com.example.habitstracker.history.domain

import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun insertAchievements(achievementEntityList: List<AchievementEntity>)

    fun getAllMyHabits(): Flow<List<HabitEntity>>

    suspend fun getAchievementOnce(): AchievementEntity?

    suspend fun getAllAchievementsOnce(): List<AchievementEntity>

    fun getAllAchievements(): Flow<List<AchievementEntity>>

    suspend fun replaceAchievements(achievements: List<AchievementEntity>)

    suspend fun updateUnlockedDate(unlockedAt: String, isNotified: Boolean, id: Int)

    fun getAllDatesForStreak(): Flow<List<DateHabitEntity>>

    suspend fun deleteHabit(habit: HabitEntity)
}