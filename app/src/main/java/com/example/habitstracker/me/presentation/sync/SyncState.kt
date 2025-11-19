package com.example.habitstracker.me.presentation.sync

import com.example.habitstracker.me.presentation.component.BannerStatus


data class SyncState(
    val banner: BannerStatus = SyncBannerStatus.NONE,
    val isLoading: Boolean = false,
)

enum class SyncBannerStatus: BannerStatus {
    NONE,
    NO_INTERNET,
    SYNC_TO_CLOUD_SUCCESS,
    SYNC_TO_CLOUD_FAIL,
    SYNC_FROM_CLOUD_SUCCESS,
    SYNC_FROM_CLOUD_FAIL,
    CLOUD_DATA_DELETED,
    CLOUD_DATA_DELETED_FAILED,
}