package com.example.habitstracker.me.domain.model

/**
 * Basic profile information stored under users/{uid}/profile/main.
 */
data class UserProfile(
    val displayName: String = "",
    val avatarUrl: String? = null,
    val profileCode: String = ""
)
