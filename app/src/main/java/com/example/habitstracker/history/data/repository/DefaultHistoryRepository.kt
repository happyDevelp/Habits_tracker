package com.example.habitstracker.history.data.repository

import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.data.db.HistoryDAO
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.history.domain.HistoryRepository
import kotlinx.coroutines.flow.Flow

class DefaultHistoryRepository(private val dao: HistoryDAO) : HistoryRepository {
    override fun getAllAchievements(): Flow<List<AchievementEntity>> {
        return dao.getAllAchievement()
    }

    override fun getAllDatesForStreak(): Flow<List<DateHabitEntity>> {
        return dao.getAllDatesForStreak()
    }
}