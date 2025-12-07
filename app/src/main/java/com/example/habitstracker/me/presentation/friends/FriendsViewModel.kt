package com.example.habitstracker.me.presentation.friends

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.me.domain.model.FriendEntry
import com.example.habitstracker.me.domain.model.FriendRequest
import com.example.habitstracker.me.domain.model.UserProfile
import com.example.habitstracker.me.domain.model.UserStats
import com.example.habitstracker.me.domain.repository.FriendsRepository
import com.example.habitstracker.me.presentation.sign_in.GoogleAuthUiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    private val authClient: GoogleAuthUiClient
) : ViewModel() {

    data class UiState(
        val friends: List<FriendEntry> = emptyList(),
        val friendStats: UserStats? = null,
        val incomingRequests: List<FriendRequest> = emptyList(),
        val addFriendInput: String = "",
        val isSending: Boolean = false,
        val error: String? = null,
        val infoMessage: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authClient.authState
                .collect { user ->
                    if (user == null) {
                        _state.value = UiState() // reset
                    } else {
                        observeForUser(user.userId)
                    }
                }
        }
    }

    private fun observeForUser(userId: String) {
        viewModelScope.launch {
            friendsRepository.observeFriends(userId).collect { list ->
                _state.update { it.copy(friends = list) }
            }
        }
        viewModelScope.launch {
            friendsRepository.observeIncomingRequests(userId).collect { list ->
                _state.update { it.copy(incomingRequests = list) }
            }
        }
    }

    fun onAddFriendInputChange(value: String) {
        _state.update { it.copy(addFriendInput = value, error = null, infoMessage = null) }
    }

    fun onAddFriendClicked() {
        val code = _state.value.addFriendInput.trim()
        Log.d("FriendsViewModel", "onAddFriendClicked: $code")
        val user = authClient.getSignedInUser() ?: return

        if (code.isEmpty()) {
            _state.update { it.copy(error = "Friend ID cannot be empty") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSending = true, error = null, infoMessage = null) }
            try {
                val fromProfile = UserProfile(
                    displayName = user.userName ?: "",
                    avatarUrl = user.profilePictureUrl,
                    friendCode = user.userId
                )
                friendsRepository.sendFriendRequest(
                    currentUserId = user.userId,
                    fromUser = fromProfile,
                    targetFriendCode = code
                )
                _state.update {
                    it.copy(
                        isSending = false,
                        addFriendInput = "",
                        infoMessage = "Request sent"
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isSending = false,
                        error = e.message ?: "Failed to send request"
                    )
                }
            }
        }
    }

    fun onAcceptRequest(request: FriendRequest) {
        val user = authClient.getSignedInUser() ?: return
        viewModelScope.launch {
            try {
                friendsRepository.acceptRequest(user.userId, request)
            } catch (_: Exception) {
            }
        }
    }

    fun onRejectRequest(request: FriendRequest) {
        val user = authClient.getSignedInUser() ?: return
        viewModelScope.launch {
            try {
                friendsRepository.rejectRequest(user.userId, request.id)
            } catch (_: Exception) {
            }
        }
    }

    fun getFriendStats(friendUserId: String) {
        viewModelScope.launch {
            val stats = friendsRepository.getFriendStats(friendUserId)
            _state.update { it.copy(friendStats = stats) }
        }
    }

    fun consumeMessage() {
        _state.update { it.copy(error = null, infoMessage = null) }
    }
}