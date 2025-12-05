package com.example.habitstracker.me.domain.repository

import com.example.habitstracker.me.domain.model.FriendEntry
import com.example.habitstracker.me.domain.model.FriendRequest
import com.example.habitstracker.me.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Manages friend relationships and incoming requests.
 */
interface FriendsRepository {
    fun observeFriends(currentUserId: String): Flow<List<FriendEntry>>
    fun observeIncomingRequests(currentUserId: String): Flow<List<FriendRequest>>

    suspend fun sendFriendRequest(currentUserId: String, fromUser: UserProfile, targetFriendCode: String)
    suspend fun acceptRequest(currentUserId: String, request: FriendRequest)
    suspend fun rejectRequest(currentUserId: String, requestId: String)
}
