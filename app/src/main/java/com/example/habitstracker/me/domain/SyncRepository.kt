package com.example.habitstracker.me.domain

import com.example.habitstracker.habit.domain.HabitEntity

interface SyncRepository {
    suspend fun syncFromCloud(userId: String): Boolean
    suspend fun syncToCloud(userId: String): Boolean
    suspend fun clearCloud(userId: String): Boolean
    suspend fun downloadOnlyHabits(userId: String): List<HabitEntity>

    suspend fun testDeleteLocalData()

}