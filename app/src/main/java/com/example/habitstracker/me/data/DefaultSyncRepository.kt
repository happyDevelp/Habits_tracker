package com.example.habitstracker.me.data

import android.util.Log
import com.example.habitstracker.me.data.local.LocalSyncRepository
import com.example.habitstracker.me.data.remote.CloudSyncRepository
import com.example.habitstracker.me.domain.SyncRepository
import javax.inject.Inject

class DefaultSyncRepository @Inject constructor(
    private val cloud: CloudSyncRepository,
    private val local: LocalSyncRepository,

    ): SyncRepository {
    override suspend fun syncFromCloud(userId: String): Boolean {
        return try {
            val habits = cloud.downloadHabits(userId)
            val dates = cloud.downloadDates(userId)

            Log.d("SYNC", "Downloading: habits=${habits.size}, dates=${dates.size}")


            local.clearAllLocalData()
            local.insertHabits(habits)
            local.insertDates(dates)

            Log.d("SYNC", "Download OK")

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

            Log.d("SYNC", "Uploading: habits=${habits.size}, dates=${dates.size}")

            cloud.uploadHabits(userId, habits)
            cloud.uploadDates(userId, dates)

            Log.d("SYNC", "Upload OK")
            true
        } catch (e: Exception) {
            Log.e("SYNC", "syncToCloud failed", e)
            false
        }
    }
}