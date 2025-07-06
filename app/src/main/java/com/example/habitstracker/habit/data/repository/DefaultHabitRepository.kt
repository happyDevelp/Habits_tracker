package com.example.habitstracker.habit.data.repository

import com.example.habitstracker.habit.data.db.HabitDao
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.HabitRepository
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.ShownHabit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

// The Domain layer is independent of data sources.

class DefaultHabitRepository(private val habitDao: HabitDao) : HabitRepository {
    override suspend fun addHabit(habit: HabitEntity): Long {
        return withContext(Dispatchers.IO) {
            habitDao.insertHabit(habit)
        }
    }

    override suspend fun getAllDatesByHabitId(id: Int): List<DateHabitEntity> {
        return withContext(Dispatchers.IO) {
            habitDao.getAllDatesByHabitId(id)
        }
    }

    override suspend fun getLastAvailableDate(): DateHabitEntity? {
        return withContext(Dispatchers.IO) {
            habitDao.getLastAvailableDate()
        }
    }

    override suspend fun updateDateSelectState(id: Int, isDone: Boolean, selectDate: String) {
        return withContext(Dispatchers.IO) {
            habitDao.updateDateSelectState(id, isDone, selectDate)
        }
    }

    override suspend fun deleteHabit(id: Int) {
        return withContext(Dispatchers.IO) {
            habitDao.deleteHabit(id)
        }
    }

    override suspend fun updateHabit(habit: HabitEntity) {
        return withContext(Dispatchers.IO) {
            habitDao.updateHabit(habit)
        }
    }

    override suspend fun insertHabitDate(habitDate: DateHabitEntity) {
        return withContext(Dispatchers.IO) {
            habitDao.insertHabitDate(habitDate)
        }
    }

    override suspend fun getHabitsByDate(date: String): Flow<List<ShownHabit>> {
        return withContext(Dispatchers.IO) {
            habitDao.getHabitsByDate(date) // YYYY-MM-DD
        }
    }

    override suspend fun dateExistsForHabit(habitId: Int, date: String): Boolean {
        return habitDao.dateExistsForHabit(habitId, date)
    }
}