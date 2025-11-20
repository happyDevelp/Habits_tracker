package com.example.habitstracker.me.presentation.sync

import android.content.Context
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
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

    suspend fun uploadHabitToCloud(habit: HabitEntity, dateHabit: DateHabitEntity): Boolean {
        val isSignedIn = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.pushHabitToCloud(isSignedIn.userId, habit, dateHabit)
    }

    suspend fun updateHabitOnCloud(habit: HabitEntity): Boolean {
        val isSignedIn = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.updateHabitOnCloud(isSignedIn.userId, habit)
    }

    suspend fun updateDateHabitOnCloud(dateHabitId: String, date: String, isDone: Boolean): Boolean {
        val isSignedIn = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.updateDateHabitOnCloud(isSignedIn.userId, dateHabitId, date, isDone)
    }

    suspend fun deleteHabitOnCloud(habitId: String): Boolean {
        val isSignedIn = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.deleteHabitOnCloud(isSignedIn.userId, habitId)
    }

    suspend fun clearCloud(): Boolean {
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.clearCloud(user.userId)
    }
}
