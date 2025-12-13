package com.example.habitstracker.me.domain.model

/**
 * Represents a confirmed friend stored under users/{uid}/friends/{friendUserId}.
 */
data class FriendEntry(
    val friendUserId: String = "",
    val friendDisplayName: String = "",
    val friendAvatarUrl: String? = null,
    val friendSince: Long = 0L
)
