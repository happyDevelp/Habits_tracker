package com.example.habitstracker.me.presentation.sync

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.me.data.local.AppPreferences
import com.example.habitstracker.me.domain.model.UserProfile
import com.example.habitstracker.me.domain.model.UserStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val syncManager: SyncManager,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(SyncState())
    val state = _state.asStateFlow()

    private val _profileState: MutableStateFlow<UserProfile?> = MutableStateFlow(null)
    val profileState = _profileState.asStateFlow()

    init {
        // 1. Listen to the time of the last synchronization
        viewModelScope.launch {
            preferences.lastSync.collect { lastSync ->
                _state.update { it.copy(lastSync = lastSync) }
            }
        }

        // 2. Listen to the locally saved ID (Profile Code)
        viewModelScope.launch {
            preferences.profileCode.collect { savedCode ->
                Log.d("SyncViewModel", "Loaded profile code: $savedCode")

                if (!savedCode.isNullOrEmpty()) {
                    _profileState.update { currentProfile ->
                        Log.d("SyncViewModel", "Current profile: $currentProfile")
                        // If there is no profile yet (null), create a "stub" with the code
                        // If there is, just update the code field
                        currentProfile?.copy(profileCode = savedCode) ?: UserProfile(profileCode = savedCode)
                    }
                }
            }
        }
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            val profile = syncManager.getUserProfile()

            if (profile != null) {
                _profileState.value = profile
                if (profile.profileCode.isNotEmpty()) {
                    preferences.saveProfileCode(profile.profileCode)
                }
            }
        }
    }

    fun syncFromCloud() {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch
            _state.update { it.copy(isLoading = true) }

            val success = syncManager.syncFromCloud()

            _state.update { it.copy(isLoading = false, lastSync = updateLastSync()) }
            if (success) showBanner(SyncBannerStatus.SYNC_FROM_CLOUD_SUCCESS)
            else showBanner(SyncBannerStatus.SYNC_FROM_CLOUD_FAIL)
        }
    }

    fun syncToCloud() {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch

            _state.update { it.copy(syncInProgress = true) }
            val success = syncManager.syncToCloud()

            if (success) {
                _state.update { it.copy(lastSync = updateLastSync(), syncInProgress = false) }
                showBanner(SyncBannerStatus.SYNC_TO_CLOUD_SUCCESS)
            } else showBanner(SyncBannerStatus.SYNC_TO_CLOUD_FAIL)
        }
    }

    fun pushHabitToCloud(habit: HabitEntity) {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch
            syncManager.pushHabitToCloud(habit)
        }
    }

    fun pushAchievementToCloud(achievement: AchievementEntity) {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch
            syncManager.pushAchievementToCloud(achievement)
        }
    }

    fun updateHabitOnCloud(habit: HabitEntity) {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch
            syncManager.updateHabitOnCloud(habit)
        }
    }

    fun updateDateHabitOnCloud(dateHabitId: String, isDone: Boolean) {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch
            syncManager.updateDateHabitOnCloud(dateHabitId, isDone)
        }
    }

    fun deleteHabitOnCloud(habitId: String) {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch
            syncManager.deleteHabitOnCloud(habitId)
        }
    }

    fun clearCloudData() {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch

            _state.update { it.copy(isLoading = true, lastSync = updateLastSync()) }
            syncManager.clearCloud()
            showBanner(status = SyncBannerStatus.CLOUD_DATA_DELETED)
        }
    }

    fun fullSync() {
        viewModelScope.launch {
            val localHabits = syncManager.getAllLocalHabits()
            val localDates = syncManager.getAllLocalDates()
            val localAchievements = syncManager.getAllLocalAchievements()

            /*val cloudHabits = syncManager.getAllCloudHabits()
            val cloudDates = syncManager.getAllCloudDates()*/

            // push missing habits
            syncManager.pushHabitToCloud(localHabits)
            syncManager.pushDateHabitToCloud(localDates)
            syncManager.pushAchievementsToCloud(localAchievements)
            _state.update { it.copy(lastSync = updateLastSync()) }
        }
    }

    suspend fun updateMyStatsOnCloud(stats: UserStats) {
        syncManager.pushUserStats(stats)
    }

    private suspend fun internetAvailable(): Boolean {
        return if (syncManager.hasInternet() == false) {
            showBanner(SyncBannerStatus.NO_INTERNET)
            false
        } else true
    }

    private suspend fun showBanner(status: SyncBannerStatus) {
        _state.update { it.copy(banner = status) }
        resetBanner()
    }

    private suspend fun resetBanner() {
        _state.update { it.copy(isLoading = false) }
        delay(3000)
        _state.update { it.copy(banner = SyncBannerStatus.NONE) }
    }

    private suspend fun updateLastSync(): String {
        val time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        preferences.saveLastSync(time)
        return time
    }
}