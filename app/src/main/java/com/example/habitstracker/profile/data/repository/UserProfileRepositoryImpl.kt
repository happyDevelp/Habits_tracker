package com.example.habitstracker.profile.data.repository

import com.example.habitstracker.profile.data.remote.firebase.UserFirebaseDataSource
import com.example.habitstracker.profile.domain.model.UserProfile
import com.example.habitstracker.profile.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Firestore-backed implementation of [UserProfileRepository].
 */
class UserProfileRepositoryImpl @Inject constructor(
    private val firebase: UserFirebaseDataSource
) : UserProfileRepository {
    override fun observeUserProfile(userId: String): Flow<UserProfile?> =
        firebase.observeUserProfile(userId)

    override suspend fun ensureUserProfile(userId: String, displayName: String?, avatarUrl: String?) {
        firebase.ensureUserProfile(userId, displayName, avatarUrl)
    }
}
