package com.example.habitstracker.me.domain.model

data class FriendEntry(
    val userId: String = "",
    val displayName: String = "",
    val avatarUrl: String? = null,
    val since: Long = 0L
)