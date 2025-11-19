package com.example.habitstracker.me.data

import android.util.Log
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.me.data.local.LocalSyncRepository
import com.example.habitstracker.me.data.remote.CloudSyncRepository
import com.example.habitstracker.me.domain.SyncRepository
import javax.inject.Inject

class DefaultSyncRepository @Inject constructor(
    private val local: LocalSyncRepository,
    private val cloud: CloudSyncRepository,
    ): SyncRepository {
    override suspend fun syncFromCloud(userId: String): Boolean {
        return try {
            val habits = cloud.downloadHabits(userId)
            val dates = cloud.downloadDates(userId)

            local.insertHabits(habits)
            local.insertDates(dates)
            local.fillMissingDatesForAllHabits()

            true
        } catch (e: Exception) {
            Log.e("SYNC", "syncToCloud failed", e)
            false
        }
    }

    override suspend fun syncToCloud(userId: String): Boolean {
        return try {
            val habits = local.getAllHabitsOnce()
            val dates = local.getAllDateHabitsOnce()

            cloud.uploadHabits(userId, habits)
            cloud.uploadDates(userId, dates)

            true
        } catch (e: Exception) {
            Log.e("SYNC", "syncToCloud failed", e)
            false
        }
    }

    override suspend fun clearCloud(userId: String): Boolean {
        return try {
            cloud.clearCloud(userId)
            true
        } catch (e: Exception) {
            Log.e("SYNC", "clearCloud failed", e)
            false
        }
    }

    override suspend fun downloadOnlyHabits(userId: String): List<HabitEntity> {
        return cloud.downloadHabits(userId)
    }
}