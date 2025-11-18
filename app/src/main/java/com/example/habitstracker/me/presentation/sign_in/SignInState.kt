package com.example.habitstracker.me.presentation.sign_in

data class SignInState(
    val banner: SignInBannerStatus = SignInBannerStatus.NONE,
    val isLoading: Boolean = false,
    val loginSuccessful: Boolean = false,
    val signInError: String? = null,
    val userData: UserData? = null
)

enum class SignInBannerStatus: BannerStatus {
    NONE,
    LOGIN_SUCCESS,
    LOGOUT_SUCCESS,
    LOGIN_FAILED,
    ACCOUNT_DELETED,
    DELETE_FAILED,
    NO_INTERNET
}

interface BannerStatus