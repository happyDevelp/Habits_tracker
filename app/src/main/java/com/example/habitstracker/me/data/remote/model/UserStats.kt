package com.example.habitstracker.me.data.remote.model

data class UserStats(
    val totalCompletedHabits: Int = 0,
    val bestStreak: Int = 0,
    val currentStreak: Int = 0,
    val perfectDaysTotal: Int = 0,
    val perfectDaysThisWeek: Int = 0,
    val consistencyPercent: Int = 0,
    val updatedAt: com.google.firebase.Timestamp? = null
)