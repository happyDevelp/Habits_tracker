package com.olesmalysh.habitstracker.habit.domain

import com.olesmalysh.habitstracker.habit.data.db.HabitWithDateDb
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun insertHabit(habit: HabitEntity): Long

    suspend fun insertHabitDates(list: List<DateHabitEntity>)

    suspend fun updateDateSelectState(id: Int, isDone: Boolean, selectDate: String)

    suspend fun deleteHabit(id: Int)

    suspend fun getLastAvailableDate(): DateHabitEntity?

    suspend fun getAllDatesByHabitIdOnce(habitId: Int): List<DateHabitEntity>

    suspend fun getLastDateForHabit(habitId: Int): DateHabitEntity?

    suspend fun getLastDateStringForHabit(habitId: Int): String?

    suspend fun countIncompleteForDate(date: String): Int

    suspend fun updateHabit(habit: HabitEntity)

    suspend fun getAllDatesByHabitId(id: Int): List<DateHabitEntity>

    suspend fun insertHabitDate(habitDate: DateHabitEntity)

    fun getHabitsByDate(date: String): Flow<List<HabitWithDateDb>> // YYYY-MM-DD

    suspend fun dateExistsForHabit(habitId: Int, date: String): Boolean

     fun getAllHabits(): Flow<List<HabitEntity>>

    suspend fun getAllHabitsOnce(): List<HabitEntity>

    fun getDateHabitsFor(date: String): Flow<List<DateHabitEntity>>

    fun getAllDateHabits(): Flow<List<DateHabitEntity>>
}