package com.example.habitstracker.history.data.repository

import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.data.db.HistoryDAO
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.history.domain.HistoryRepository
import com.example.habitstracker.history.domain.StatisticEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DefaultHistoryRepository(private val dao: HistoryDAO) : HistoryRepository {
    override suspend fun updateUnlockedDate(unlockedAt: String, isNotified: Boolean, id: Int) {
        return withContext(Dispatchers.IO) {
            dao.updateUnlockedDate(unlockedAt, isNotified, id)
        }
    }

    override fun getAllAchievements(): Flow<List<AchievementEntity>> {
        return dao.getAllAchievement()
    }

    override fun getAllDatesForStreak(): Flow<List<DateHabitEntity>> {
        return dao.getAllDatesForStreak()
    }

    override suspend fun insertStatistic(statisticEntity: StatisticEntity) {
        dao.insertStatistic(statisticEntity)
    }

    override suspend fun updateStatistic(completedHabits: Int, bestStreak: Int, perfectDays: Int) {
        return withContext(Dispatchers.IO) {
            dao.updateStatistic(completedHabits, bestStreak, perfectDays)
        }
    }

    override fun getStatistic(): Flow<StatisticEntity> {
        return dao.getStatistic()
    }

    override suspend fun getStatisticOnce(): StatisticEntity? {
        return withContext(Dispatchers.IO) {
            dao.getStatisticOnce()
        }
    }
}