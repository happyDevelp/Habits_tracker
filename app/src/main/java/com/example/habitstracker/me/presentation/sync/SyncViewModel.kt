package com.example.habitstracker.me.presentation.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.me.domain.SyncRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val syncRepo: SyncRepository,
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _state = MutableStateFlow(SyncState())
    val state = _state.asStateFlow()

    private val userId: String?
        get() = auth.currentUser?.uid


    fun syncFromCloud() {
        val uid = userId ?: return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val success = syncRepo.syncFromCloud(uid)

            _state.update {
                it.copy(
                    isLoading = false, banner = if (success) SyncBannerStatus.SYNC_SUCCESS
                    else SyncBannerStatus.SYNC_FAILED
                )
            }
        }
    }

    fun syncToCloud() {
        val uid = userId ?: return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val success = syncRepo.syncToCloud(uid)

            _state.update {
                it.copy(
                    isLoading = false,
                    banner = if (success) SyncBannerStatus.SYNC_SUCCESS
                    else SyncBannerStatus.SYNC_FAILED,
                    buttonState = if (success) SyncButtonState.SUCCESS
                    else SyncButtonState.ERROR
                )

            }
            delay(3000)
            _state.update { it.copy(buttonState = SyncButtonState.IDLE) }
        }
    }

    fun resetBanner() {
        _state.update { it.copy(banner = SyncBannerStatus.NONE) }
    }

    fun deleteLocalData() {
        viewModelScope.launch {
            syncRepo.testDeleteLocalData()
        }
    }
}