package com.example.habitstracker.me.presentation


enum class BannerStatus {
    NONE,
    NO_INTERNET,
    SYNC_SUCCESS,
    SYNC_FAILED
}

data class MeScreenState(
    val banner: BannerStatus = BannerStatus.NONE,
    val isLoading: Boolean = false
)