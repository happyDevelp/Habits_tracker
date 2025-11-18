package com.example.habitstracker.me.presentation.sync

import com.example.habitstracker.me.presentation.sign_in.BannerStatus

data class SyncState(
    val banner: BannerStatus = SyncBannerStatus.NONE,
    val isLoading: Boolean = false,
    val buttonState: SyncButtonState = SyncButtonState.IDLE
)

enum class SyncBannerStatus: BannerStatus {
    NONE,
    NO_INTERNET,
    SYNC_SUCCESS,
    SYNC_FAILED
}

enum class SyncButtonState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR
}