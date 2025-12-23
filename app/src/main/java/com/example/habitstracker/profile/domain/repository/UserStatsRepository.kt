package com.example.habitstracker.profile.domain.repository

import com.example.habitstracker.profile.domain.model.UserStats
import kotlinx.coroutines.flow.Flow

/**
 * Reads and writes user statistics stored in Firestore.
 */
interface UserStatsRepository {
    fun observeUserStats(userId: String): Flow<UserStats?>
    suspend fun pushUserStats(userId: String, stats: UserStats)
}
