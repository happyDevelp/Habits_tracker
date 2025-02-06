package com.example.habitstracker.habit.domain

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun addHabit(habit: HabitEntity): Long

    suspend fun updateDateSelectState(id: Int, isDone: Boolean, selectDate: String)

    suspend fun deleteHabit(id: Int)

    suspend fun getLastAvailableDate(): DateHabitEntity?

    suspend fun updateHabit(habit: HabitEntity)

    suspend fun getAllDatesByHabitId(id: Int): List<DateHabitEntity>

    suspend fun insertHabitDate(habitDate: DateHabitEntity)

    suspend fun getHabitsByDate(date: String): Flow<List<ShownHabit>> // YYYY-MM-DD
}