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

            //Log.d("SYNC", "Downloading: habits=${habits.size}, dates=${dates.size}")


            local.clearAllLocalData()
            local.insertHabits(habits)
            local.insertDates(dates)
            //Log.d("SYNC", "Download OK")

            local.fillMissingDatesForAllHabits()

            true
        } catch (e: Exception) {
            //Log.e("SYNC", "syncToCloud failed", e)
            false
        }
    }

    override suspend fun syncToCloud(userId: String): Boolean {
        return try {
            val habits = local.getAllHabitsOnce()
            val dates = local.getAllDateHabitsOnce()

            //Log.d("SYNC", "Uploading: habits=${habits.size}, dates=${dates.size}")

            cloud.uploadHabits(userId, habits)
            cloud.uploadDates(userId, dates)

            //Log.d("SYNC", "Upload OK")
            true
        } catch (e: Exception) {
            //Log.e("SYNC", "syncToCloud failed", e)
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



    override suspend fun testDeleteLocalData() {
        local.clearAllLocalData()
    }
}