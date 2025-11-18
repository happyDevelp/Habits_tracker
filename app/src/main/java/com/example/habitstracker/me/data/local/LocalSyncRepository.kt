package com.example.habitstracker.me.data.local

import com.example.habitstracker.habit.data.db.HabitDao
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LocalSyncRepository @Inject constructor(private val dao: HabitDao) {
    suspend fun clearAllLocalData() {
        dao.clearDateTable()
        dao.clearHabitTable()
    }

    suspend fun getAllHabitsOnce(): List<HabitEntity> {
        return dao.getAllHabits().first()
    }

    suspend fun getAllDateHabitsOnce(): List<DateHabitEntity> {
        return dao.getAllDateHabits().first()
    }

    suspend fun insertHabits(list: List<HabitEntity>) {
        list.forEach { dao.insertHabit(it) }
    }

    suspend fun insertDates(list: List<DateHabitEntity>) {
        list.forEach { dao.insertHabitDate(it) }
    }
}