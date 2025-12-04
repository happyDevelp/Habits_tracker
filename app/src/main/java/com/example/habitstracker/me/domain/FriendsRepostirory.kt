package com.example.habitstracker.me.domain

import com.example.habitstracker.me.domain.model.FriendEntry
import com.example.habitstracker.me.domain.model.FriendRequest
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {

    /** List of friends of the current user */
    fun observeFriends(userId: String): Flow<List<FriendEntry>>

    /** Incoming pending friend requests */
    fun observeIncomingRequests(userId: String): Flow<List<FriendRequest>>

    /** Відправити запит в друзі по friendCode */
    suspend fun sendFriendRequest(
        fromUserId: String,
        fromDisplayName: String,
        fromAvatarUrl: String?,
        targetFriendCode: String
    )

    /** Accept Inquiry */
    suspend fun acceptRequest(
        currentUserId: String,
        request: FriendRequest
    )

    /** Reject Request */
    suspend fun rejectRequest(
        currentUserId: String,
        requestId: String
    )
}