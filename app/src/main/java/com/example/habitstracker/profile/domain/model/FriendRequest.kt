package com.example.habitstracker.profile.domain.model

/**
 * Incoming friend request under users/{uid}/friendRequests/{fromUserId}.
 */
data class FriendRequest(
    val id: String = "", // document id = fromUserId
    val fromUserId: String = "",
    val fromDisplayName: String = "",
    val fromAvatarUrl: String? = null,
    val sentAt: Long = 0L
)
