package com.example.habitstracker.me.data

import android.util.Log
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.me.data.local.LocalSyncRepository
import com.example.habitstracker.me.data.remote.CloudSyncRepository
import com.example.habitstracker.me.domain.SyncRepository
import javax.inject.Inject

class DefaultSyncRepository @Inject constructor(
    private val local: LocalSyncRepository,
    private val cloud: CloudSyncRepository,
) : SyncRepository {
    override suspend fun syncFromCloud(userId: String): Boolean {
        return try {
            val habits = cloud.getHabits(userId)
            val dates = cloud.getDates(userId)
            val achievements = cloud.getAchievements(userId)

            var isUpdated = false

            if (habits.isNotEmpty()) {
                local.replaceAllFromCloud(habits, dates)
                isUpdated = true
            } else {
                Log.d("SYNC_DEBUG", "syncFromCloud: cloud is empty, skip replacing local data")
            }

            if (achievements.isNotEmpty()) {
                local.replaceAchievementsFromCloud(achievements)
                isUpdated = true
            }

            isUpdated
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "syncFromCloud failed", e)
            false
        }
    }


    override suspend fun syncToCloud(userId: String): Boolean {
        return try {
            val habits = local.getAllHabitsOnce()
            val dates = local.getAllDateHabitsOnce()
            val achievements = local.getAllAchievementsOnce()

            cloud.pushHabit(userId, habits)
            cloud.pushDateHabit(userId, dates)
            cloud.pushAchievements(userId, achievements)

            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "syncToCloud failed", e)
            false
        }
    }

    override suspend fun pushHabitToCloud(
        userId: String,
        habit: HabitEntity,
    ): Boolean {
        return try {
            cloud.pushHabit(userId, habit)
            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "pushHabitToCloud failed", e)
            false
        }
    }

    // overloaded
    override suspend fun pushHabitToCloud(
        userId: String,
        habitsList: List<HabitEntity>
    ): Boolean {
        return try {
            cloud.pushHabit(userId, habitsList)
            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "pushHabitToCloud failed", e)
            false
        }
    }

    override suspend fun pushDateHabitToCloud(
        userId: String,
        dateHabit: DateHabitEntity
    ): Boolean {
        return try {
            cloud.pushDateHabit(userId, dateHabit)
            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "pushHabitToCloud failed", e)
            false
        }
    }

    override suspend fun pushDateHabitToCloud(
        userId: String,
        dateHabit: List<DateHabitEntity>
    ): Boolean {
        return try {
            cloud.pushDateHabit(userId, dateHabit)
            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "pushHabitToCloud failed", e)
            false
        }
    }

    override suspend fun pushAchievementsToCloud(
        userId: String,
        achievements: List<AchievementEntity>
    ): Boolean {
        return try {
            cloud.pushAchievements(userId, achievements)
            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "pushAchievementsToCloud failed", e)
            false
        }
    }

    override suspend fun updateHabitOnCloud(userId: String, habit: HabitEntity): Boolean {
        return try {
            cloud.updateHabit(userId, habit)
            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "updateHabitInCloud failed", e)
            false
        }
    }

    override suspend fun updateSelectStateOnCloud(
        userId: String,
        dateHabitId: String,
        isDone: Boolean
    ): Boolean {
        return try {
            cloud.updateSelectState(
                userId = userId,
                dateHabitId = dateHabitId,
                isDone = isDone
            )
            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "updateDateHabitInCloud failed", e)
            false
        }
    }

    override suspend fun deleteHabitOnCloud(
        userId: String,
        habitId: String
    ): Boolean {
        return try {
            cloud.deleteHabit(userId, habitId)
            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "deleteHabitOnCloud failed", e)
            false
        }
    }

    override suspend fun clearCloud(userId: String): Boolean {
        return try {
            cloud.clearCloud(userId)
            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "clearCloud failed", e)
            false
        }
    }

    override suspend fun downloadHabitsFromLocal(userId: String): List<HabitEntity> {
        return try {
            local.getAllHabitsOnce()
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "downloadHabits failed", e)
            emptyList()
        }
    }

    override suspend fun downloadDatesFromLocal(userId: String): List<DateHabitEntity> {
        return try {
            local.getAllDateHabitsOnce()
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "downloadDates failed", e)
            emptyList()
        }
    }

    override suspend fun downloadAchievementsFromLocal(userId: String): List<AchievementEntity> {
        return try {
            local.getAllAchievementsOnce()
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "downloadAchievements failed", e)
            emptyList()
        }
    }

    override suspend fun downloadHabitsFromCloud(userId: String): List<HabitEntity> {
        return try {
            cloud.getHabits(userId)
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "downloadHabits failed", e)
            emptyList()
        }
    }

    override suspend fun downloadDatesFromCloud(userId: String): List<DateHabitEntity> {
        return try {
            cloud.getDates(userId)
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "downloadDates failed", e)
            emptyList()
        }
    }

    override suspend fun downloadAchievementsFromCloud(userId: String): List<AchievementEntity> {
        return try {
            cloud.getAchievements(userId)
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "downloadAchievements failed", e)
            emptyList()
        }
    }

    override suspend fun syncAchievementsFromCloud(userId: String): Boolean {
        return try {
            val achievements = cloud.getAchievements(userId)
            if (achievements.isEmpty()) {
                Log.d("SYNC_DEBUG", "syncAchievementsFromCloud: cloud is empty")
                return false
            }
            local.replaceAchievementsFromCloud(achievements)
            true
        } catch (e: Exception) {
            Log.e("SYNC_DEBUG", "syncAchievementsFromCloud failed", e)
            false
        }
    }
}