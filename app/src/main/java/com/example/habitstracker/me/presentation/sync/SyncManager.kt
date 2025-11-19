package com.example.habitstracker.me.presentation.sync

import android.content.Context
import com.example.habitstracker.me.domain.SyncRepository
import com.example.habitstracker.me.presentation.sign_in.GoogleAuthUiClient
import com.example.habitstracker.me.presentation.sign_in.isInternetAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SyncManager@Inject constructor(
    private val syncRepo: SyncRepository,
    private val googleAuthUiClient: GoogleAuthUiClient,
    @ApplicationContext private val context: Context,
) {

    fun hasInternet(): Boolean = context.isInternetAvailable()

    suspend fun syncToCloud(): Boolean {
        val isSignedIn = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.syncToCloud(isSignedIn.userId)
    }

    suspend fun syncFromCloud(): Boolean {
        val isSignedIn = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.syncFromCloud(isSignedIn.userId)
    }

    suspend fun clearCloud(): Boolean {
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.clearCloud(user.userId)
    }

    suspend fun testDeleteLocalData() {
        syncRepo.testDeleteLocalData()
    }
}
