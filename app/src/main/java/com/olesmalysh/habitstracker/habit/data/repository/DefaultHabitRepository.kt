package com.olesmalysh.habitstracker.habit.data.repository

import com.olesmalysh.habitstracker.habit.data.db.HabitDao
import com.olesmalysh.habitstracker.habit.data.db.HabitWithDateDb
import com.olesmalysh.habitstracker.habit.domain.DateHabitEntity
import com.olesmalysh.habitstracker.habit.domain.HabitEntity
import com.olesmalysh.habitstracker.habit.domain.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID

// The Domain layer is independent of data sources.

class DefaultHabitRepository(private val habitDao: HabitDao) : HabitRepository {
    override suspend fun insertHabit(habit: HabitEntity): Long {
        return withContext(Dispatchers.IO) {

            val habitToInsert = if (habit.uid.isBlank())
                habit.copy(uid = UUID.randomUUID().toString())
            else habit

            habitDao.insertHabit(habitToInsert)
        }
    }

    override suspend fun insertHabitDates(list: List<DateHabitEntity>) {
        return withContext(Dispatchers.IO) {
            habitDao.insertHabitDates(list)
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

    override suspend fun getAllDatesByHabitIdOnce(habitId: Int): List<DateHabitEntity> {
        return withContext(Dispatchers.IO) {
            habitDao.getAllDatesByHabitIdOnce(habitId)
        }
    }

    override suspend fun getLastDateForHabit(habitId: Int): DateHabitEntity? {
        return withContext(Dispatchers.IO) {
            habitDao.getLastDateForHabit(habitId)
        }
    }

    override suspend fun getLastDateStringForHabit(habitId: Int): String? {
        return withContext(Dispatchers.IO) {
            habitDao.getLastDateStringForHabit(habitId)
        }
    }

    override suspend fun countIncompleteForDate(date: String): Int {
        return withContext(Dispatchers.IO) {
            habitDao.countIncompleteForDate(date)
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

    override fun getHabitsByDate(date: String): Flow<List<HabitWithDateDb>> {
        return habitDao.getHabitsByDate(date) // YYYY-MM-DD
    }

    override suspend fun dateExistsForHabit(habitId: Int, date: String): Boolean {
        return withContext(Dispatchers.IO) {
            habitDao.dateExistsForHabit(habitId, date)
        }
    }

    override fun getAllHabits(): Flow<List<HabitEntity>> {
        return habitDao.getAllHabits()
    }

    override suspend fun getAllHabitsOnce(): List<HabitEntity> {
        return withContext(Dispatchers.IO) {
            habitDao.getAllHabitsOnce()
        }
    }

    override fun getDateHabitsFor(date: String): Flow<List<DateHabitEntity>> {
        return habitDao.getDateHabitsFor(date)
    }

    override fun getAllDateHabits(): Flow<List<DateHabitEntity>> {
        return habitDao.getAllDateHabits()
    }
}