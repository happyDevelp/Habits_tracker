package com.example.habitstracker.me.data.repository

import com.example.habitstracker.me.data.remote.firebase.UserFirebaseDataSource
import com.example.habitstracker.me.domain.model.UserStats
import com.example.habitstracker.me.domain.repository.UserStatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Firestore-backed implementation of [UserStatsRepository].
 */
class UserStatsRepositoryImpl @Inject constructor(
    private val firebase: UserFirebaseDataSource
) : UserStatsRepository {
    override fun observeUserStats(userId: String): Flow<UserStats?> = firebase.observeUserStats(userId)

    override suspend fun pushUserStats(userId: String, stats: UserStats) {
        firebase.pushUserStats(userId, stats)
    }
}
