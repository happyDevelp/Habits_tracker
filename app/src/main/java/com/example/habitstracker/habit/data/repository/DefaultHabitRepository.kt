package com.example.habitstracker.habit.data.repository

import com.example.habitstracker.habit.data.db.DAO
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.HabitRepository
import com.example.habitstracker.habit.domain.HabitStatusEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

// The Domain layer is independent of data sources.

class DefaultHabitRepository(private val dao: DAO) : HabitRepository {
    override suspend fun addHabit(habit: HabitEntity) {
        withContext(Dispatchers.IO) {
            dao.addHabit(habit)
        }
    }

    override suspend fun deleteHabit(habit: HabitEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteHabit(habit)
        }
    }

    override suspend fun getAllHabits(): Flow<List<HabitEntity>> {
        return withContext(Dispatchers.IO) {
            dao.getAllHabits()
        }
    }

    override suspend fun updateSelectedState(id: Int, isDone: Boolean) {
        return withContext(Dispatchers.IO) {
            dao.updateSelectedState(id, isDone)
        }
    }

    override suspend fun getHabitByName(name: String): HabitEntity? {
        return withContext(Dispatchers.IO) {
            dao.getHabitByName(name)
        }
    }

    override suspend fun getHabitById(id: Int): HabitEntity? {
        return withContext(Dispatchers.IO) {
            dao.getHabitById(id)
        }
    }

    override suspend fun updateHabit(habit: HabitEntity) {
        return withContext(Dispatchers.IO) {
            dao.updateHabit(habit)
        }
    }

    override suspend fun insertHabitStatus(status: HabitStatusEntity) {
        return withContext(Dispatchers.IO) {
            dao.insertHabitStatus(status)
        }
    }

    override suspend fun getHabitsByDate(date: String): Flow<List<HabitEntity>> {
        return withContext(Dispatchers.IO) {
            dao.getHabitsByDate(date) // YYYY-MM-DD
        }
    }
}