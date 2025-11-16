package com.example.habitstracker.me.presentation.sign_in

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val userData: UserData? = null,
    val isLoading: Boolean = false
)