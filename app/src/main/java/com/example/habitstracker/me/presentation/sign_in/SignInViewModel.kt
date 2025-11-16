package com.example.habitstracker.me.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    init {
        val currentUser = googleAuthUiClient.getSignedInUser()

        if(currentUser != null) {
            _state.update {
                it.copy(
                    isSignInSuccessful = true,
                    userData = currentUser
                )
            }
        }
    }

    fun signIn() {
        viewModelScope.launch {
            _state.update { it.copy(signInError = null, isLoading = true) }

            val isSuccess = googleAuthUiClient.signIn()

            if (isSuccess) {
                _state.update {
                    it.copy(
                        isSignInSuccessful = true,
                        userData = googleAuthUiClient.getSignedInUser(),
                        isLoading = false
                    )
                }
            } else {
                _state.update { it.copy(signInError = "Sign-in failed", isLoading = false) }
            }
        }
    }

    fun signOut() {
        _state.update { it.copy(isLoading = true) }
        googleAuthUiClient.signOut()
        _state.update { SignInState() }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            googleAuthUiClient.deleteAccount()
            _state.update { SignInState() }
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}