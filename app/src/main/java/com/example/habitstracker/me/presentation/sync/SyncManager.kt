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
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.syncToCloud(user.userId)
    }

    suspend fun syncFromCloud(): Boolean {
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.syncFromCloud(user.userId)
    }

    suspend fun pushHabitToCloud(habit: HabitEntity): Boolean {
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.pushHabitToCloud(user.userId, habit)
    }

    suspend fun pushHabitToCloud(habitsList: List<HabitEntity>): Boolean {
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.pushHabitToCloud(user.userId, habitsList)
    }

    suspend fun pushDateHabitToCloud(dateHabit: DateHabitEntity): Boolean {
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.pushDateHabitToCloud(user.userId, dateHabit)
    }

    suspend fun pushDateHabitToCloud(dateHabitsList: List<DateHabitEntity>): Boolean {
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.pushDateHabitToCloud(user.userId, dateHabitsList)
    }

    suspend fun updateHabitOnCloud(habit: HabitEntity): Boolean {
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.updateHabitOnCloud(user.userId, habit)
    }

    suspend fun updateDateHabitOnCloud(dateHabitId: String, date: String, isDone: Boolean): Boolean {
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.updateDateHabitOnCloud(user.userId, dateHabitId, date, isDone)
    }

    suspend fun deleteHabitOnCloud(habitId: String): Boolean {
        val isSignedIn = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.deleteHabitOnCloud(isSignedIn.userId, habitId)
    }

    suspend fun clearCloud(): Boolean {
        val user = googleAuthUiClient.getSignedInUser() ?: return false
        return syncRepo.clearCloud(user.userId)
    }

    suspend fun getAllLocalHabits(): List<HabitEntity>{
        val user = googleAuthUiClient.getSignedInUser() ?: return emptyList()
        return syncRepo.downloadHabitsFromLocal(user.userId)
    }

    suspend fun getAllLocalDates(): List<DateHabitEntity> {
        val user = googleAuthUiClient.getSignedInUser() ?: return emptyList()
        return syncRepo.downloadDatesFromLocal(user.userId)
    }

    suspend fun getAllCloudHabits(): List<HabitEntity>{
        val user = googleAuthUiClient.getSignedInUser() ?: return emptyList()
        return syncRepo.downloadHabitsFromCloud(user.userId)
    }

    suspend fun getAllCloudDates(): List<DateHabitEntity> {
        val user = googleAuthUiClient.getSignedInUser() ?: return emptyList()
        return syncRepo.downloadDatesFromCloud(user.userId)
    }
}
