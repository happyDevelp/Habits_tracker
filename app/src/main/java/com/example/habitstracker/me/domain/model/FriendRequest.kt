package com.example.habitstracker.me.domain.model

data class FriendRequest(
    val id: String = "",
    val fromUserId: String = "",
    val toUserId: String = "",
    val fromDisplayName: String = "",
    val fromAvatarUrl: String? = null,

    val status: String = "pending",   // pending | accepted | rejected
    val sentAt: Long = 0L,            // timestamp
    val handledAt: Long? = null       // When accepted/rejected
)