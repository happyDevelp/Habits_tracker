package com.example.habitstracker.profile.data.repository

import com.example.habitstracker.profile.data.remote.firebase.UserFirebaseDataSource
import com.example.habitstracker.profile.domain.model.FriendEntry
import com.example.habitstracker.profile.domain.model.FriendRequest
import com.example.habitstracker.profile.domain.model.UserProfile
import com.example.habitstracker.profile.domain.model.UserStats
import com.example.habitstracker.profile.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Firestore-backed implementation of [FriendsRepository].
 */
class FriendsRepositoryImpl @Inject constructor(
    private val firebase: UserFirebaseDataSource
) : FriendsRepository {
    override fun observeFriends(currentUserId: String): Flow<List<FriendEntry>> =
        firebase.observeFriends(currentUserId)

    override fun observeIncomingRequests(currentUserId: String): Flow<List<FriendRequest>> =
        firebase.observeIncomingRequests(currentUserId)

    override suspend fun sendFriendRequest(currentUserId: String, fromUser: UserProfile, targetProfileCode: String) {
        firebase.sendFriendRequest(fromUser, currentUserId, targetProfileCode)
    }

    override suspend fun acceptRequest(currentUserId: String, request: FriendRequest) {
        firebase.acceptFriendRequest(currentUserId, request)
    }

    override suspend fun rejectRequest(currentUserId: String, requestId: String) {
        firebase.rejectFriendRequest(currentUserId, requestId)
    }

    override suspend fun getFriendStats(friendUserId: String): UserStats? {
        return firebase.getFriendStats(friendUserId)
    }

    override suspend fun deleteFriend(currentUserId: String, friendUserId: String) {
        return firebase.deleteFriend(currentUserId, friendUserId)
    }
}
