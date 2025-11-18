/*
package com.example.habitstracker.me.presentation.sync

import com.example.habitstracker.me.data.local.LocalSyncRepository
import com.example.habitstracker.me.data.remote.CloudSyncRepository
import javax.inject.Inject

class SyncManager@Inject constructor(
    private val localRepo: LocalSyncRepository,
    private val remoteRepo: CloudSyncRepository
) {
    suspend fun sync(userId: String) {
        val localHabits = localRepo.getAllHabitsOnce()
        val remoteHabits = remoteRepo.downloadHabits(userId = userId)

    }
}*/
