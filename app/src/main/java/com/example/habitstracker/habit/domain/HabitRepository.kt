package com.example.habitstracker.habit.domain

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun addHabit(habit: HabitEntity): Long

    suspend fun updateHabitAndDateSelectState(id: Int, isDone: Boolean, selectDate: String)

    suspend fun deleteHabit(id: Int)

    suspend fun getAllHabits(date: String): Flow<List<HabitEntity>>

    suspend fun getDateByHabitIdAndDate(id: Int, date: String): DateHabitEntity

    suspend fun getHabitById(id: Int): HabitEntity?

    suspend fun updateHabit(habit: HabitEntity)

    suspend fun getHabitDateByDate(date: String): List<DateHabitEntity>?

    suspend fun getAllDatesByHabitId(id: Int): List<DateHabitEntity>

    // date_table
    suspend fun insertHabitDate(habitDate: DateHabitEntity)

    suspend fun getHabitsByDate(date: String): Flow<List<HabitEntity>> // YYYY-MM-DD

    suspend fun insertAll(dates: List<DateHabitEntity>)

}