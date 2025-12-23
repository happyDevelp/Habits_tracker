package com.example.habitstracker.profile.domain.repository

import com.example.habitstracker.profile.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Provides access to the user's profile document in Firestore.
 */
interface UserProfileRepository {
    fun observeUserProfile(userId: String): Flow<UserProfile?>
    suspend fun ensureUserProfile(userId: String, displayName: String?, avatarUrl: String?)
}
