package com.example.habitstracker.habit.domain

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun addHabit(habit: HabitEntity): Long

    suspend fun updateHabitAndDateSelectState(id: Int, isDone: Boolean)

    suspend fun deleteHabit(id: Int)

    suspend fun getAllHabits(): Flow<List<HabitEntity>>

    suspend fun getHabitById(id: Int): HabitEntity?

    suspend fun updateHabit(habit: HabitEntity)

    suspend fun getHabitDateByDate(date: String): List<DateHabitEntity>?

    suspend fun getAllDatesByHabitId(id: Int): List<DateHabitEntity>

    suspend fun updateHabitDatesForToday(habitId: Int, today: String)

    // date_table
    suspend fun insertHabitDate(habitDate: DateHabitEntity)

    suspend fun getHabitsByDate(date: String): Flow<List<HabitEntity>> // YYYY-MM-DD

    suspend fun insertAll(dates: List<DateHabitEntity>)

}