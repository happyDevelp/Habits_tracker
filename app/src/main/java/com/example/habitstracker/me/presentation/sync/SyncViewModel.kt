package com.example.habitstracker.me.presentation.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.me.data.local.SyncPreferences
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
    private val preferences: SyncPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(SyncState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            preferences.lastSync.collect { lastSync ->
                _state.update { it.copy(lastSync = lastSync) }
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

            /*val cloudHabits = syncManager.getAllCloudHabits()
            val cloudDates = syncManager.getAllCloudDates()*/

            // push missing habits
            syncManager.pushHabitToCloud(localHabits)
            syncManager.pushDateHabitToCloud(localDates)
            _state.update { it.copy(lastSync = updateLastSync()) }
        }
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