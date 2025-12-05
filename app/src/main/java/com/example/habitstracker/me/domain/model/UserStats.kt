package com.example.habitstracker.me.domain.model

/**
 * Aggregated statistics stored under users/{uid}/stats/stats.
 */
data class UserStats(
    val totalCompletedHabits: Int = 0,
    val bestStreak: Int = 0,
    val currentStreak: Int = 0,
    val perfectDaysTotal: Int = 0,
    val perfectDaysThisWeek: Int = 0,
    val consistencyPercent: Double = 0.0,
    val updatedAt: com.google.firebase.Timestamp? = null,
)
