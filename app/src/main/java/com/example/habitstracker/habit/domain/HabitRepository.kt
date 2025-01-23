package com.example.habitstracker.habit.domain

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun addHabit(habit: HabitEntity)

    suspend fun deleteHabit(habit: HabitEntity)

    suspend fun getAllHabits(): Flow<List<HabitEntity>>

    suspend fun updateSelectedState(id: Int, isDone: Boolean)

    suspend fun getHabitByName(name: String): HabitEntity?

    suspend fun getHabitById(id: Int): HabitEntity?

    suspend fun updateHabit(habit: HabitEntity)


    // date_table
    suspend fun insertHabitDate(habitDate: HabitStatusEntity)

    suspend fun getHabitsByDate(date: String): Flow<List<HabitEntity>> // YYYY-MM-DD


}