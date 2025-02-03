package com.example.habitstracker.habit.data.repository

import com.example.habitstracker.habit.data.db.DAO
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.HabitRepository
import com.example.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

// The Domain layer is independent of data sources.

class DefaultHabitRepository(private val dao: DAO) : HabitRepository {
    override suspend fun addHabit(habit: HabitEntity): Long {
        return withContext(Dispatchers.IO) {
            dao.insertHabit(habit)
        }
    }

    override suspend fun getAllDatesByHabitId(id: Int): List<DateHabitEntity> {
        return withContext(Dispatchers.IO) {
            dao.getAllDatesByHabitId(id)
        }
    }

    override suspend fun updateHabitAndDateSelectState(id: Int, isDone: Boolean, selectDate: String) {
        return withContext(Dispatchers.IO) {
            dao.updateHabitAndDateSelectState(id, isDone, selectDate)
        }
    }

    override suspend fun deleteHabit(id: Int) {
        return withContext(Dispatchers.IO) {
            dao.deleteHabit(id)
        }
    }

    override suspend fun getAllHabits(): Flow<List<HabitEntity>> {
        return withContext(Dispatchers.IO) {
            dao.getAllHabits()
        }
    }

    override suspend fun updateHabit(habit: HabitEntity) {
        return withContext(Dispatchers.IO) {
            dao.updateHabit(habit)
        }
    }

    override suspend fun insertHabitDate(habitDate: DateHabitEntity) {
        return withContext(Dispatchers.IO) {
            dao.insertHabitDate(habitDate)
        }
    }

    override suspend fun getHabitsByDate(date: String): Flow<List<HabitEntity>> {
        return withContext(Dispatchers.IO) {
            dao.getHabitsByDate(date) // YYYY-MM-DD
        }
    }
}