package com.example.habitstracker.profile.presentation.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.profile.domain.model.FriendEntry
import com.example.habitstracker.profile.domain.model.FriendRequest
import com.example.habitstracker.profile.domain.model.UserProfile
import com.example.habitstracker.profile.domain.model.UserStats
import com.example.habitstracker.profile.domain.repository.FriendsRepository
import com.example.habitstracker.profile.presentation.component.BannerStatus
import com.example.habitstracker.profile.presentation.friends.components.FriendsBannerStatus
import com.example.habitstracker.profile.presentation.sign_in.GoogleAuthUiClient
import com.example.habitstracker.profile.presentation.sync.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FriendsState(
    val friends: List<FriendEntry> = emptyList(),
    val friendStats: UserStats = UserStats(),
    val banner: BannerStatus = FriendsBannerStatus.NONE,
    val incomingRequests: List<FriendRequest> = emptyList(),
    val isSending: Boolean = false,
)

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    private val authClient: GoogleAuthUiClient,
    private val syncManager: SyncManager
) : ViewModel() {

    private val _state = MutableStateFlow(FriendsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authClient.authState
                .collect { user ->
                    if (user == null) {
                        _state.value = FriendsState() // reset
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

    fun onAddFriendClicked(profileCodeInput: String) {
        val user = authClient.getSignedInUser() ?: return

        // 1. Clean up the spaces
        val code = profileCodeInput.trim()

        viewModelScope.launch {
            if (syncManager.hasInternet() == false) {
                showBanner(FriendsBannerStatus.NO_INTERNET)
                return@launch
            }

            _state.update { it.copy(isSending = true) }
            try {
                val fromProfile = UserProfile(
                    displayName = user.userName ?: "",
                    avatarUrl = user.profilePictureUrl,
                    profileCode = user.userId
                )
                friendsRepository.sendFriendRequest(
                    currentUserId = user.userId,
                    fromUser = fromProfile,
                    targetProfileCode = code
                )
                showBanner(FriendsBannerStatus.REQUEST_SENT_SUCCESS)
            } catch (e: IllegalArgumentException) {
                // Handling specific bugs (logic from the repository\UserFirebaseDataSource)
                val msg = e.message ?: ""

                when {
                    // The user is probably trying to add themselves
                    msg.contains("yourself", ignoreCase = true) -> {
                        showBanner(FriendsBannerStatus.CANNOT_ADD_SELF)
                    }

                    msg.contains("already friends", ignoreCase = true) -> {
                        showBanner(FriendsBannerStatus.ALREADY_FRIENDS)
                    }

                    else -> {
                        // probably impossible to find a user with that ID
                        showBanner(FriendsBannerStatus.REQUEST_FAILED)
                    }
                }
            } catch (e: Exception) {
                showBanner(FriendsBannerStatus.REQUEST_FAILED)
                e.printStackTrace()
            } finally {
                _state.update { it.copy(isSending = false) }
            }
        }
    }

    private suspend fun showBanner(status: FriendsBannerStatus) {
        _state.update { it.copy(banner = status) }
        delay(3000)
        _state.update { it.copy(banner = FriendsBannerStatus.NONE) }
    }

    fun onAcceptRequest(request: FriendRequest) {
        val user = authClient.getSignedInUser() ?: return
        viewModelScope.launch {
            /*if (syncManager.hasInternet() == false) {
                showBanner(FriendsBannerStatus.NO_INTERNET)
                return@launch
            }*/
            friendsRepository.acceptRequest(user.userId, request)
        }
    }

    fun onRejectRequest(request: FriendRequest) {
        val user = authClient.getSignedInUser() ?: return
        viewModelScope.launch {
           /* if (syncManager.hasInternet() == false) {
                showBanner(FriendsBannerStatus.NO_INTERNET)
                return@launch
            }*/
            friendsRepository.rejectRequest(user.userId, request.id)
        }
    }

    fun getFriendStats(friendUserId: String) {
        viewModelScope.launch {
/*            if(syncManager.hasInternet() == false) {
                showBanner(FriendsBannerStatus.NO_INTERNET)
                return@launch
            }*/
            val stats = friendsRepository.getFriendStats(friendUserId) ?: UserStats()
            _state.update { it.copy(friendStats = stats) }
        }
    }

    fun deleteFriend(friendUserId: String) {
        val currentUserId = authClient.getSignedInUser()?.userId ?: return
        viewModelScope.launch {
            friendsRepository.deleteFriend(currentUserId, friendUserId)
        }
    }
}
