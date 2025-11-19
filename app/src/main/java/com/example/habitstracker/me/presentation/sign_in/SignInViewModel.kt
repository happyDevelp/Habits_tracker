package com.example.habitstracker.me.presentation.sign_in

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.me.domain.SyncRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val googleAuthUiClient: GoogleAuthUiClient,
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    init {
        val currentUser = googleAuthUiClient.getSignedInUser()

        if(currentUser != null) {
            _state.update {
                it.copy(
                    userData = currentUser
                )
            }
        }
    }

    fun signIn() {
        viewModelScope.launch {

            if (context.isInternetAvailable() == false) {
                _state.update {
                    it.copy(
                        signInError = "No internet connection",
                        isLoading = false,
                    )
                }
                showBanner(status = SignInBannerStatus.NO_INTERNET)
                return@launch // exit from coroutine
            }

            _state.update { it.copy(signInError = null, isLoading = true) }

            val isSuccess = googleAuthUiClient.signIn()

            if (isSuccess) {
                _state.update {
                    it.copy(
                        userData = googleAuthUiClient.getSignedInUser(),
                        isLoading = false,
                        loginSuccessful = true
                    )
                }
                showBanner(status = SignInBannerStatus.LOGIN_SUCCESS)
            } else {
                _state.update { it.copy(signInError = "Sign-in failed", isLoading = false) }
                showBanner(status = SignInBannerStatus.LOGIN_FAILED)
            }
        }
    }

    fun signOut() {
        if (context.isInternetAvailable() == false) {
            _state.update {
                it.copy(
                    signInError = "No internet connection",
                    isLoading = false,
                )
            }
            showBanner(status = SignInBannerStatus.NO_INTERNET)
            return // exit from function
        }
        _state.update { it.copy(isLoading = true) }
        googleAuthUiClient.signOut()
        _state.update { SignInState() }
        showBanner(status = SignInBannerStatus.LOGOUT_SUCCESS)
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    private fun showBanner(status: SignInBannerStatus) {
        viewModelScope.launch {
            _state.update { it.copy(banner = status) }
            delay(2500)
            _state.update { it.copy(banner = SignInBannerStatus.NONE) }
        }
    }
}