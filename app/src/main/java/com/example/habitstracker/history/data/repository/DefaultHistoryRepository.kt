package com.example.habitstracker.history.data.repository

import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.data.db.HistoryDAO
import com.example.habitstracker.history.domain.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DefaultHistoryRepository(private val dao: HistoryDAO): HistoryRepository {
    override suspend fun getAllDatesForStreak(): Flow<List<DateHabitEntity>> {
         return withContext(Dispatchers.IO) {
             dao.getAllDatesForStreak()
         }
    }
}