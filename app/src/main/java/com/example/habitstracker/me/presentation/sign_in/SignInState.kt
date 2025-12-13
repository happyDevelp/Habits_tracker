package com.example.habitstracker.me.presentation.sign_in

import com.example.habitstracker.me.presentation.component.BannerStatus

data class SignInState(
    val banner: BannerStatus = SignInBannerStatus.NONE,
    val isLoading: Boolean = false,
    val loginSuccessful: Boolean = false,
    val signInError: String? = null,
    val userData: UserData? = null
)

enum class SignInBannerStatus : BannerStatus {
    NONE,
    LOGIN_SUCCESS,
    LOGOUT_SUCCESS,
    LOGIN_FAILED,
    NO_INTERNET
}