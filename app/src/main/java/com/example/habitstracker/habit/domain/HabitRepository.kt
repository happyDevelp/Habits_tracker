package com.example.habitstracker.habit.domain

import com.example.habitstracker.habit.data.db.HabitWithDateDb
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun insertHabit(habit: HabitEntity): Long

    suspend fun updateDateSelectState(id: Int, isDone: Boolean, selectDate: String)

    suspend fun deleteHabit(id: Int)

    suspend fun getLastAvailableDate(): DateHabitEntity?

    suspend fun updateHabit(habit: HabitEntity)

    suspend fun getAllDatesByHabitId(id: Int): List<DateHabitEntity>

    suspend fun insertHabitDate(habitDate: DateHabitEntity)

    fun getHabitsByDate(date: String): Flow<List<HabitWithDateDb>> // YYYY-MM-DD

    suspend fun dateExistsForHabit(habitId: Int, date: String): Boolean

     fun getAllHabits(): Flow<List<HabitEntity>>

    fun getDateHabitsFor(date: String): Flow<List<DateHabitEntity>>

    fun getAllDateHabits(): Flow<List<DateHabitEntity>>
}