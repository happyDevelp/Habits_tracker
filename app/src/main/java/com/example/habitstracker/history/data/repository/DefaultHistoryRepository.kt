package com.example.habitstracker.history.data.repository

import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.history.data.db.HistoryDAO
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.history.domain.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DefaultHistoryRepository(private val dao: HistoryDAO) : HistoryRepository {

    override suspend fun insertAchievements(achievementEntityList: List<AchievementEntity>) {
        dao.insertAchievements(achievementEntityList)
    }

    override fun getAllMyHabits(): Flow<List<HabitEntity>> {
        return dao.getALlMyHabits()
    }

    override suspend fun updateUnlockedDate(unlockedAt: String, isNotified: Boolean, id: Int) {
        return withContext(Dispatchers.IO) {
            dao.updateUnlockedDate(unlockedAt, isNotified, id)
        }
    }

    override suspend fun getAchievementOnce(): AchievementEntity? {
        return withContext(Dispatchers.IO) {
            dao.getAchievementOnce()
        }
    }

    override suspend fun getAllAchievementsOnce(): List<AchievementEntity> {
        return withContext(Dispatchers.IO) {
            dao.getAllAchievementsOnce()
        }
    }

    override fun getAllAchievements(): Flow<List<AchievementEntity>> {
        return dao.getAllAchievement()
    }

    override suspend fun replaceAchievements(achievements: List<AchievementEntity>) {
        return withContext(Dispatchers.IO) {
            dao.clearAchievements()
            dao.insertAchievements(achievements)
        }
    }

    override fun getAllDatesForStreak(): Flow<List<DateHabitEntity>> {
        return dao.getAllDatesForStreak()
    }

    override suspend fun deleteHabit(habit: HabitEntity) {
        return withContext(Dispatchers.IO) {
            dao.deleteHabit(habit)
        }
    }
}