package com.example.habitstracker.me.domain

import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity

interface SyncRepository {
    suspend fun syncFromCloud(userId: String): Boolean
    suspend fun syncToCloud(userId: String): Boolean

    suspend fun pushHabitToCloud(userId: String, habit: HabitEntity, dateHabit: DateHabitEntity): Boolean
    suspend fun pushDateHabitToCloud(userId: String, dateHabit: DateHabitEntity): Boolean
    suspend fun updateHabitOnCloud(userId: String, habit: HabitEntity): Boolean
    suspend fun updateDateHabitOnCloud(userId: String, dateHabitId: String, date: String, isDone: Boolean): Boolean
    suspend fun deleteHabitOnCloud(userId: String, habitId: String): Boolean

    suspend fun clearCloud(userId: String): Boolean
    suspend fun downloadHabitsFromLocal(userId: String): List<HabitEntity>
    suspend fun downloadDatesFromLocal(userId: String): List<DateHabitEntity>

    suspend fun downloadHabitsFromCloud(userId: String): List<HabitEntity>
    suspend fun downloadDatesFromCloud(userId: String): List<DateHabitEntity>
}