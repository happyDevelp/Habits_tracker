package com.example.habitstracker.profile.domain.repository

import com.example.habitstracker.profile.domain.model.FriendEntry
import com.example.habitstracker.profile.domain.model.FriendRequest
import com.example.habitstracker.profile.domain.model.UserProfile
import com.example.habitstracker.profile.domain.model.UserStats
import kotlinx.coroutines.flow.Flow

/**
 * Manages friend relationships and incoming requests.
 */
interface FriendsRepository {
    fun observeFriends(currentUserId: String): Flow<List<FriendEntry>>
    fun observeIncomingRequests(currentUserId: String): Flow<List<FriendRequest>>

    suspend fun sendFriendRequest(currentUserId: String, fromUser: UserProfile, targetProfileCode: String)
    suspend fun acceptRequest(currentUserId: String, request: FriendRequest)
    suspend fun rejectRequest(currentUserId: String, requestId: String)
    suspend fun getFriendStats(friendUserId: String): UserStats?
    suspend fun deleteFriend(currentUserId: String, friendUserId: String)

}
