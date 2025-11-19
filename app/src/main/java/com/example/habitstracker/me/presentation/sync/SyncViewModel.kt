package com.example.habitstracker.me.presentation.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(private val syncManager: SyncManager) : ViewModel() {

    private val _state = MutableStateFlow(SyncState())
    val state = _state.asStateFlow()

    fun syncFromCloud() {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch

            _state.update { it.copy(isLoading = true) }

            val success = syncManager.syncFromCloud()

            _state.update { it.copy(isLoading = false) }
            if (success) showBanner(SyncBannerStatus.SYNC_FROM_CLOUD_SUCCESS)
            else showBanner(SyncBannerStatus.SYNC_FROM_CLOUD_FAIL)
            resetBanner()
        }
    }

    fun syncToCloud() {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch

            _state.update { it.copy(isLoading = true) }

            val success = syncManager.syncToCloud()

            _state.update { it.copy(isLoading = false) }
            if (success) showBanner(SyncBannerStatus.SYNC_TO_CLOUD_SUCCESS)
            else showBanner(SyncBannerStatus.SYNC_TO_CLOUD_FAIL)
            resetBanner()

        }
    }

    fun deleteLocalData() {
        viewModelScope.launch {
            syncManager.testDeleteLocalData()
        }
    }

    fun clearCloudData() {
        viewModelScope.launch {
            if (internetAvailable() == false) return@launch

            _state.update { it.copy(isLoading = true) }
            syncManager.clearCloud()
            _state.update { it.copy(isLoading = false) }
            showBanner(status = SyncBannerStatus.CLOUD_DATA_DELETED)
            resetBanner()
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
        delay(3000)
        _state.update { it.copy(banner = SyncBannerStatus.NONE) }
    }
}