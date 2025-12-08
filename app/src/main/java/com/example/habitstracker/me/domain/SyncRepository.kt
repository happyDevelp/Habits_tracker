package com.example.habitstracker.me.domain

import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.me.domain.model.UserProfile
import com.example.habitstracker.me.domain.model.UserStats

interface SyncRepository {
    suspend fun syncFromCloud(userId: String): Boolean
    suspend fun syncToCloud(userId: String): Boolean

    suspend fun pushHabitToCloud(userId: String, habit: HabitEntity): Boolean

    // overloaded
    suspend fun pushHabitToCloud(userId: String, habitsList: List<HabitEntity>): Boolean


    suspend fun pushDateHabitToCloud(userId: String, dateHabit: DateHabitEntity): Boolean

    // overloaded
    suspend fun pushDateHabitToCloud(userId: String, dateHabit: List<DateHabitEntity>): Boolean

    suspend fun pushAchievementsToCloud(
        userId: String,
        achievements: List<AchievementEntity>
    ): Boolean


    suspend fun updateHabitOnCloud(userId: String, habit: HabitEntity): Boolean
    suspend fun updateSelectStateOnCloud(
        userId: String,
        dateHabitId: String,
        isDone: Boolean
    ): Boolean

    suspend fun deleteHabitOnCloud(userId: String, habitId: String): Boolean

    suspend fun clearCloud(userId: String): Boolean
    suspend fun downloadHabitsFromLocal(userId: String): List<HabitEntity>
    suspend fun downloadDatesFromLocal(userId: String): List<DateHabitEntity>
    suspend fun downloadAchievementsFromLocal(userId: String): List<AchievementEntity>

    suspend fun downloadHabitsFromCloud(userId: String): List<HabitEntity>
    suspend fun downloadDatesFromCloud(userId: String): List<DateHabitEntity>
    suspend fun downloadAchievementsFromCloud(userId: String): List<AchievementEntity>

    suspend fun syncAchievementsFromCloud(userId: String): Boolean

    suspend fun ensureUserProfile(userId: String, displayName: String?, avatarUrl: String?): UserProfile?
    suspend fun getUserProfile(userId: String): UserProfile?
    suspend fun findUserIdByProfileCode(friendCode: String): String?
    suspend fun pushUserStats(userId: String, stats: UserStats): Boolean
}